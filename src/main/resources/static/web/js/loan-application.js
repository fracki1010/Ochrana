Vue.createApp({
    data() {
        return {
            clientInfo: [],
            errorToats: null,
            errorMsg: null,
            loanTypes: [],
            loanTypeId: 0,
            loanName:'',
            interest: 0,
            payments: 0,
            paymentsList: [],
            clientAccounts: [],
            errorToats: null,
            errorMsg: null,
            accountToNumber: "VIN",
            amount: 0,
            fees: []
        }
    },
    methods: {
        getDataClient: function () {
            axios.get("/api/clients/current")
                 .then((response) => {
                        //obtiene los datos del cliente actual y autenticado
                        this.clientInfo = response.data;
                 })
                 .catch((error) => {
                        // Por si no hay un cliente autenticado y no obtiene nada
                        this.errorMsg = "Error getting data";
                        this.errorToats.show();
                 })
        },
        getData: function () {
            Promise.all([axios.get("/api/loans"), axios.get("/api/clients/current/accounts")])
                .then((response) => {
                    //get loan types info
                    this.loanTypes = response[0].data;
                    let clientAccountsFilter = response[1].data;
                    this.clientAccounts = clientAccountsFilter.filter(clientAccount => clientAccount.approved);

                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        checkApplication: function () {
            if (this.loanTypeId == 0) {
                this.errorMsg = "You must select a loan type";
                this.errorToats.show();
            }
            else if (this.payments == 0) {
                this.errorMsg = "You must select payments";
                this.errorToats.show();
            }
            else if (this.accountToNumber == "VIN") {
                this.errorMsg = "You must indicate an account";
                this.errorToats.show();
            }
            else if (this.amount == 0) {
                this.errorMsg = "You must indicate an amount";
                this.errorToats.show();
            } else {
                this.modal.show();
            }
        },
        apply: function () {
            axios.post("/api/loans", { loanId: this.loanTypeId, amount: this.amount, payments: this.payments, toAccountNumber: this.accountToNumber })
                .then(response => {
                    this.modal.hide();
                    this.okmodal.show();
                })
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        },
        changedType: function () {
            this.paymentsList = this.loanTypes.find(loanType => loanType.id == this.loanTypeId).payments;
            this.loanName = this.loanTypes.find(loanType => loanType.id == this.loanTypeId).name;

        },
        bankInterest: function(payment, loanName) {
                    if (loanName == "Hipotecario"){
                        switch (payment) {
                            case 12:
                                return 1.2;
                            case 24:
                                return 1.4;
                            case 36:
                                return 1.6;
                            case 48:
                                return 1.8;
                            case 60:
                                return 1.9;
                            default:
                                return 0.0;
                        }
                    } else if (loanName == "Personal") {
                        switch (payment){
                            case 6:
                                return 1.1;
                            case 12:
                                return 1.3;
                            case 24:
                                return 1.6;
                            default:
                                return 0.0;
                        }
                    } else if (loanName == "Automotriz") {
                        switch (payment){
                            case 12:
                                return 1.3;
                            case 24:
                                return 1.6;
                            case 36:
                                return 1.8;
                            default:
                                return 0.0;
                        }
                    }
                    return null;
                },
        finish: function () {
            window.location.href = "/web/cards.html";
        },
        checkFees: function () {
            this.fees = [];
            this.totalLoan = parseInt(this.amount * this.bankInterest(this.payments, this.loanName));
            this.interest = parseInt((this.bankInterest(this.payments, this.loanName)-1)*100);
            let amount = this.totalLoan / this.payments;
            for (let i = 1; i <= this.payments; i++) {
                this.fees.push({ amount: amount });
            }
            this.feesmodal.show();
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.feesmodal = new bootstrap.Modal(document.getElementById('feesModal'));
        this.getData();
        this.getDataClient();
    }
}).mount('#app')