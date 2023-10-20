Vue.createApp({
    data() {
        return {
            clientInfo: {},
            errorToats: null,
            errorMsg: '',
            cardType: "DEBIT",
            cardColor: "SILVER"
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
        activeModal: function() {
            this.modal.show();
        },
        create: function () {
            if (this.cardType == "none" || this.cardColor == "none") {
                this.errorMsg = "You must select a card type and color";
                this.errorToats.show();
            } else {
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
                axios.post(`/api/clients/current/cards?cardType=${this.cardType}&cardColor=${this.cardColor}`, config)
                    .then(response => {
                        this.modal.hide();
                        this.okmodal.show();
                    })
                    .catch((error) => {
                        if (error.response && error.response.status === 403) {
                            const errorResponse = error.response;
                            if (errorResponse.data) {
                                // Acceder al texto de respuesta del error
                                const errorText = errorResponse.data;
                                this.errorMsg = errorText;
                                this.errorToats.show();
                            }
                            this.activateError();
                        }
                    })
            }
        },
        finish: function () {
            window.location.reload();
        },
        activateError: function(error) {
            this.modal.hide();
            this.errormodal.show();
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.errormodal = new bootstrap.Modal(document.getElementById('errorModal'));
        this.getDataClient();
    }
}).mount('#app')