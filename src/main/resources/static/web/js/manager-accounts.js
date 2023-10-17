Vue.createApp({
    data() {
        return {
            clientInfo: {},
            accountsInfo: [],
            filterAccountsInfo: [],
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
        getAllAccountsData: function () {
            axios.get("/api/accounts")
                .then((response) => {
                //Obtener las cuentas de cada cliente registrado
                    this.accountsInfo = response.data;
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        bottomFilterAccounts: function (){
            this.filterAccountsInfo = this.accountsInfo.filter(account => account.number.toLowerCase().includes(this.search));

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
        this.getAllAccountsData();
    },
    computed:{
        searchIsEmpty: function (){
            if (this.search.length === 0){
                this.filterAccountsInfo = this.accountsInfo;
            }
            console.log(this.search)
        }
    }
}).mount('#app')