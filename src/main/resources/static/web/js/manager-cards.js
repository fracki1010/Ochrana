Vue.createApp({
    data() {
        return {
            clientInfo: {},
            cardsInfo: [],
            filterCardsInfo: [],
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
        getAllCardsData: function () {
            axios.get("/api/cards")
                .then((response) => {
                //Obtener las tarjetas de cada cliente registrado
                    this.cardsInfo = response.data;
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        bottomFilterCards: function (){
            this.filterCardsInfo = this.cardsInfo
                                   .filter(card => card.number.toLowerCase().includes(this.search.toLowerCase())
                                   || card.type.toLowerCase().includes(this.search.toLowerCase())
                                   || card.color.toLowerCase().includes(this.search.toLowerCase())
                                   || card.cvv.toString().includes(this.search.toLowerCase()));
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
        this.getAllCardsData();
    },
    computed:{
        searchIsEmpty: function (){
            if (this.search.length === 0){
                this.filterCardsInfo = this.cardsInfo;
            }
            console.log(this.cardsInfo);
            console.log(this.search)
        }
    }
}).mount('#app')