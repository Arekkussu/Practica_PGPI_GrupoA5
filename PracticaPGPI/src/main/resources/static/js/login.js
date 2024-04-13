document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const correo = document.getElementById('floatingInput').value;
        const contrasena = document.getElementById('floatingPassword').value;

        // Validaciones
        if (!validateEmail(correo)) {
            showModal('Error', 'Por favor, ingresa un correo electrónico válido.');
            return;
        }

        if (!contrasena.trim()) {
            showModal('Error', 'Por favor, ingresa tu contraseña.');
            return;
        }

        const loginData = {
            correo: correo,
            contrasena: contrasena
        };

        fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to log in: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                window.location.href = '/inicio';
            })
            .catch((error) => {
                console.error('Error:', error);
                showModal('Error', 'Hubo un error al iniciar sesión. Por favor, inténtalo de nuevo.');
            });
    });

    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(String(email).toLowerCase());
    }

    function showModal(title, message) {
        document.getElementById('modalLabel').textContent = title;
        document.getElementById('modalMessage').textContent = message;
        const modalButton = document.getElementById('modalButton');

        // Configura el evento click para cerrar el modal
        modalButton.onclick = function() {
            $('#messageModal').modal('hide');
        };

        $('#messageModal').modal('show');
    }
});