Vue.createApp({
    data() {
        return {
            clientInfo: {},
            loansInfo: [],
            filterLoansInfo: [],
            search: '',
            errorToats: null,
            errorMsg: null,
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
        getAllLoansData: function () {
            axios.get("/api/client-loans")
                .then((response) => {
                //Obtener las tarjetas de cada cliente registrado
                    this.loansInfo = response.data;
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        bottomFilterLoans: function (){
            this.filterLoansInfo = this.loansInfo
                                   .filter(loan => loan.payments.toString().includes(this.search)
                                   || loan.name.toLowerCase().includes(this.search)
                                   || loan.toAccountTransfer.toLowerCase().includes(this.search)
                                   || loan.amount.toString().includes(this.search));
                                   console.log(filterLoansInfo);
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getDataClient();
        this.getAllLoansData();
    },
    computed:{
        searchIsEmpty: function (){
            if (this.search.length === 0){
                this.filterLoansInfo = this.loansInfo;
            }

        }
    }
}).mount('#app')