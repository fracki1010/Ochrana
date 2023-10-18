Vue.createApp({
    data() {
        return {
            clientInfo: [],
            accountInfo: {},
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
        getData: function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            axios.get(`/api/accounts/${id}`)
                .then((response) => {
                    //get client ifo
                    this.accountInfo = response.data;
                    this.accountInfo.transactions.sort((a, b) => (b.id - a.id))
                })
                .catch((error) => {
                    // handle error
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
        generatePDF: function () {
            // Crear un nuevo documento jsPDF
            let doc = new jsPDF();

            // Encabezados de las columnas
            let headers = ['N°', 'Description', 'Date', 'Amount'];

            // Datos de ejemplo
            let accountSelect = this.accountInfo.transactions;
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
        this.getData();
        this.getDataClient();
    }
}).mount('#app')