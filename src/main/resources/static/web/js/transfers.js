Vue.createApp({
    data() {
        return {
            clientAccounts: [],
            clientAccountsTo: [],
            filterAccountOwner: [],
            ownerAccountTo: '',
            clientInfo: [],
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
            accountFromNumber: "VIN",
            accountToNumber: "VIN",
            transferType: "own",
            amount: 0,
            description: ""
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
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    //obteniendo las cuentas del cliente autenticado
                    let clientAccountsData = response.data;
                    this.clientAccounts = clientAccountsData.filter(account => account.approved);
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        checkTransfer: function () {
            if (this.accountFromNumber == "VIN") {
                this.errorMsg = "You must select an origin account";
                this.errorToats.show();
            }
            else if (this.accountToNumber == "VIN") {
                this.errorMsg = "You must select a destination account";
                this.errorToats.show();
            } else if (this.amount == 0) {
                this.errorMsg = "You must indicate an amount";
                this.errorToats.show();
            }
            else if (this.description.length <= 0) {
                this.errorMsg = "You must indicate a description";
                this.errorToats.show();
            } else {
                this.modal.show();
            }
        },
        transfer: function () {
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post(`/api/transactions?fromAccountNumber=${this.accountFromNumber}&toAccountNumber=${this.accountToNumber}&amount=${this.amount}&description=${this.description}`, config)
                .then(response => {
                    this.modal.hide();
                    this.okmodal.show();
                })
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                    this.errorMsg = "This account does not have that amount"
                })
        },
        changedType: function () {
            this.accountFromNumber = "VIN";
            this.accountToNumber = "VIN";
        },
        changedFrom: function () {
            if (this.trasnferType == "own") {
                this.clientAccountsTo = this.clientAccounts.filter(account => account.number != this.accountFromNumber);
                this.accountToNumber = "VIN";
                console.log(this.clientAccountsTo);
            }
        },
        finish: function () {
            window.location.reload();
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        getAccountAll: function () {
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.get(`/api/accounts`, config)
                .then(response => {
                    this.filterAccountOwner = response.data;

                })
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                    this.errorMsg = "This account does not have that amount"
                })
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.getData();
        this.getDataClient();
        this.getAccountAll();
    },
    computed:{
        findAccountOwner: function (){
            let accountToTransfer = this.filterAccountOwner.find(account => account.number === this.accountToNumber);
            if(typeof accountToTransfer === "undefined"){
                if(this.accountToNumber.length > 3){
                this.ownerAccountTo = 'Does not exist';
                }
            }else{
                this.ownerAccountTo = accountToTransfer.owner
            }
        }
    }
}).mount('#app')