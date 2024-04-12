document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const nombre = document.getElementById('floatingName').value;
        const apellidos = document.getElementById('floatingApellidos').value;
        const correo = document.getElementById('floatingInput').value;
        const contrasena = document.getElementById('floatingPassword').value;

        // Validaciones
        if (!nombre.trim()) {
            showAlert('Por favor, ingresa tu nombre.');
            return;
        }

        if (!apellidos.trim()) {
            showAlert('Por favor, ingresa tus apellidos.');
            return;
        }

        if (!validateEmail(correo)) {
            showAlert('Por favor, ingresa un correo electrónico válido.');
            return;
        }

        if (contrasena.length < 6) {
            showAlert('La contraseña debe tener al menos 6 caracteres.');
            return;
        }
        const user = {
            nombre: nombre,
            apellidos: apellidos,
            correo: correo,
            contrasena: contrasena
        };

        fetch('/auth/registro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                $('#successModal').modal('show');
            })
            .catch((error) => {
                console.error('Error:', error);
                showAlert('Hubo un error al realizar el registro. Por favor, inténtalo de nuevo.'); // Muestra un mensaje de error al usuario
            });
    });

    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(String(email).toLowerCase());
    }

    function showAlert(message) {
        alert(message); // Considera reemplazar este alert por otro modal para consistencia.
    }
});