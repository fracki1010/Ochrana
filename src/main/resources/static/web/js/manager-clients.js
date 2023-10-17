Vue.createApp({
    data() {
        return {
            clientInfo: {},
            clientsInfo: [],
            accountsInfo: {},
            search: '',
            filterClientsInfo:[],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {

        getAllClientsData: function () {
            axios.get("/api/clients")
                .then((response) => {
                    //Obtener todos los clientes registrados
                    this.clientsInfo = response.data;
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })

        },
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
        bottomFilterClients: function (){
            this.filterClientsInfo = this.clientsInfo
                                     .filter(client => client.firstName.toLowerCase()
                                     .includes(this.search) || client.lastName.toLowerCase()
                                     .includes(this.search));

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
        this.getAllClientsData();
    },
    computed:{
         /*filterClients: function () {
            this.filterClientsInfo = this.clientsInfo
                                        .filter(client => client.firstName.toLowerCase()
                                         .includes(this.search) || client.lastName.toLowerCase()
                                         .includes(this.search));
        }*/
        searchIsEmpty: function (){
            if (this.search.length === 0){
                this.filterClientsInfo = this.clientsInfo;

            }
            console.log(this.search)
        }
    }
}).mount('#app')