Vue.createApp({
    data() {
        return {
            clientInfo: [],
            clientSearchInfo: {},
            accountsInfo: {},
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            axios.get(`/api/clients/${id}`)
                .then((response) => {
                    //get client ifo
                    this.clientSearchInfo = response.data;
                    /*this.clientSearchInfo.transactions.sort((a, b) => (b.id - a.id))*/
                })
                .catch((error) => {
                    // handle error
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
        approvedCard: function (numberCardClient)  {
            event.preventDefault();
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
            axios.post(`/api/clients/current/cards/approved?numberCard=${numberCardClient}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    // Por si no hay un cliente autenticado y no obtiene nada
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
                console.log(numberCardClient);
        },
        deleteCard: function (numberCardClient) {
            event.preventDefault();
                    let config = {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded'
                        }
                    }
            axios.post(`/api/clients/current/cards/delete?numberCard=${numberCardClient}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    // Por si no hay un cliente autenticado y no obtiene nada
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
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
        createCard: function () {
            axios.post('/api/clients/current/accounts')
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getDataClient();
        this.getData();
    }
}).mount('#app')