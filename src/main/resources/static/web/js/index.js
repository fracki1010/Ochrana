const {createApp} = Vue

const app = createApp({
    data(){
        return{
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            dni: "",
            birthdate: "",
            cuil: "",
            errorToats: null,
            errorMsg:"",
            showSignUp: false,
        }
    },
    created(){

    },
    methods:{
        //Iniciar sesion con un nuevo cliente
        logIn: function(event){
            event.preventDefault();
                    let config = {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded'
                        }
                    }
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, config)
                .then((response) => {
                    if (this.email.includes("@admin.com")) {
                      window.location.href = "/web/manager-clients.html";
                    } else {
                      window.location.href = "/web/accounts.html";
                    }
                  })
                .catch(() => {
                    this.errorMsg = "Sign in failed, check the information"
                    this.errorToats.show();
                })
        },

        //Registrar un cliente nuevo
        register: function (event) {
                    event.preventDefault();
                    let config = {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded'
                        }
                    }
                    axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}&rolType=CLIENT&dni=${this.dni}&birthdate=${this.birthdate}&cuil=${this.cuil}`, config)
                        .then(() => {
                            this.logIn(event)
                            this.errorMsg = response.text();
                            this.errorToats.show();
                        })
                        .catch(error => {
                            if (error.response && error.response.status === 403) {
                              // Acceder a la respuesta de error cuando se recibe un código 403
                              const errorResponse = error.response;
                              if (errorResponse.data) {
                                // Acceder al texto de respuesta del error
                                const errorText = errorResponse.data;
                                this.errorMsg = errorText;
                                this.errorToats.show();
                              }
                            }
                        })
                        /*.catch(() => {
                            this.errorMsg = response.text();
                            this.errorToats.show();
                        })*/
                        console.log(this.birthdate);
        },
        showSignUpToogle: function () {
                    this.showSignUp = !this.showSignUp;
        },

        formatDate: function (date) {
                    return new Date(date).toLocaleDateString('en-gb');
        },

    },
    mounted: function () {
            this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
            }
}).mount("#app")