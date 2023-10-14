Vue.createApp({
    data() {
        return {
            clientInfo: [],
            clientSearchInfo: {},
            accountsInfo: {},
            loansInfo: {},
            containerShowAccounts: true,
            containerShowCards: false,
            containerShowLoans: false,
            containerShowPending: false,
            pendingIsEmpty: false,
            cardsIsEmpty: false,
            loansIsEmpty: false,
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
        getDataLoans: function(){
            axios.get("/api/loans")
            .then((response) => {
                this.loansInfo = response.data;
            })
            .catch((error) => {
                this.errorMsg = "Error getting data";
                this.errorToats.show();
            })
        },
        showContainer: function(container){
            switch (container){
                case "accounts":
                    this.containerShowAccounts = true;
                    this.containerShowCards = false;
                    this.containerShowLoans = false;
                    this.containerShowPending = false;
                    break;
                case "cards":
                    this.containerShowAccounts = false;
                    this.containerShowCards = true;
                    this.containerShowLoans = false;
                    this.containerShowPending = false;
                break;
                case "loans":
                    this.containerShowAccounts = false;
                    this.containerShowCards = false;
                    this.containerShowLoans = true;
                    this.containerShowPending = false;
                break;
                case "pending":
                    this.containerShowAccounts = false;
                    this.containerShowCards = false;
                    this.containerShowLoans = false;
                    this.containerShowPending = true;
                    break;
            }
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
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        approvedAccount: function (numberAccount){
            event.preventDefault();
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
            axios.post(`/api/clients/current/accounts/approved?numberAccount=${numberAccount}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        deleteAccount: function (numberAccount){
            event.preventDefault();
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
            axios.post(`/api/clients/current/accounts/delete?numberAccount=${numberAccount}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        approvedLoan: function (loanNameClient ,clientLoanId1, amountLoan, toAccount){
            //Aprueba el prestamo y realiza la transferencia la cuenta elegida
            axios.post("/api/loans/approved",{loanName: loanNameClient, clientLoanId: clientLoanId1, amount: amountLoan, toAccountNumber: toAccount})
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        deleteLoan: function (clientLoanId){
            event.preventDefault();
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
            axios.post(`/api/loans/delete?loanDeleteId=${clientLoanId}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        filterApproved: function () {
        //Esto genera un msj cuando no hay tarjetas,prestamos o pendiente
            let accountsDisapproved = this.clientSearchInfo.accounts.filter(account => account.approved == false).length;
            let cardsDisapproved = this.clientSearchInfo.cards.filter(card => card.approved == false).length;
            let loansDisapproved = this.clientSearchInfo.loans.filter(loan => loan.approved == false).length;
            if(accountsDisapproved == 0 && cardsDisapproved == 0 && loansDisapproved == 0 ){
                this.pendingIsEmpty = true;
            }else{
                this.pendingIsEmpty = false;
            }
            if(cardsDisapproved == this.clientSearchInfo.cards.length){
                this.cardsIsEmpty = true;
            }else{
                this.cardsIsEmpty = false;
            }
            if(loansDisapproved == this.clientSearchInfo.loans.length){
                this.loansIsEmpty = true;
            }else{
                this.loansIsEmpty = false;
            }
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
        },
        generatePDF: function (accountNumber) {
            // Crear un nuevo documento jsPDF
            let doc = new jsPDF();

            // Encabezados de las columnas
            let headers = ['N°', 'Description', 'Date', 'Amount'];

            // Datos de ejemplo
            let accountSelect = this.clientSearchInfo.accounts.find(account => account.number === accountNumber).transactions;
            let transactionsOrder = accountSelect.sort((a, b) => (b.id - a.id))
            let transactionsData = [];
            let data = [];
            // Suponiendo que accountSelect.transactions es un arreglo de objetos

            transactionsOrder.forEach(transaction => {
              transactionsData.push(transaction.id.toString());
              transactionsData.push(transaction.description);
              transactionsData.push(transaction.date);
              transactionsData.push(transaction.amount.toString());
              data.push(transactionsData);
              transactionsData = []; // Borra los elementos del arreglo para la siguiente iteración
            });


            // Definir la posición de la tabla en el documento
            let posX = 20; // Posición X
            let posY = 40; // Posición Y

            // Configuración de la tabla
            let cellWidth = 40; // Ancho de celda
            let cellHeight = 10; // Alto de celda
            let xOffset = posX; // Inicializar la posición X de la celda
            let yOffset = posY; // Inicializar la posición Y de la celda

            // Definir el título que deseas agregar
            const title = 'OCHRANA BANK';

            // Configurar la posición y estilo del título
            const titleX = 55; // Coordenada X para centrar el título en la página
            const titleY = 20;  // Coordenada Y para ajustar la posición vertical del título
            const titleSize = 26; // Tamaño de fuente del título

            // Agregar el título al documento
            doc.setFontSize(titleSize);
            doc.text(title, titleX, titleY);

            // Definir estilos de celda
            doc.setFontSize(12);
            doc.setFont('helvetica', 'bold');

            // Dibujar encabezados de columna
            headers.forEach(function (header, index) {
              doc.text(xOffset, yOffset, header);
              xOffset += cellWidth;
            });

            // Restablecer posiciones X e Y
            xOffset = posX;
            yOffset += cellHeight;

            // Dibujar datos de la tabla
            data.forEach(function (row) {
              row.forEach(function (cell, index) {
                doc.text(xOffset, yOffset, cell);
                xOffset += cellWidth;
              });
              yOffset += cellHeight;
              xOffset = posX; // Restablecer la posición X para la siguiente fila
            });

            // Guardar el documento como "mi-tabla.pdf"
            doc.save('Transaction_history.pdf');

        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getDataClient();
        this.getDataLoans();
        this.getData();
    }
}).mount('#app')