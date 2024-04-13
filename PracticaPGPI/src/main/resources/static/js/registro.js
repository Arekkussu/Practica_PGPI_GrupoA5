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
            showModal('Error', 'Por favor, ingresa tu nombre.');
            return;
        }

        if (!apellidos.trim()) {
            showModal('Error', 'Por favor, ingresa tus apellidos.');
            return;
        }

        if (!validateEmail(correo)) {
            showModal('Error', 'Por favor, ingresa un correo electrónico válido.');
            return;
        }

        if (contrasena.length < 6) {
            showModal('Error', 'La contraseña debe tener al menos 6 caracteres.');
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
                showModal('Éxito', 'Usuario registrado correctamente.', true);
            })
            .catch((error) => {
                console.error('Error:', error);
                showModal('Error', 'Hubo un error al realizar el registro. Por favor, inténtalo de nuevo.');
            });
    });

    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(String(email).toLowerCase());
    }

    function showModal(title, message, redirect=false) {
        document.getElementById('modalLabel').textContent = title;
        document.getElementById('modalMessage').textContent = message;
        const modalButton = document.getElementById('modalButton');
        if (redirect) {
            modalButton.onclick = function() { window.location.href = ' /login'; };
        } else {
            modalButton.onclick = function() { $('#messageModal').modal('hide'); };
        }
        $('#messageModal').modal('show');
    }
});