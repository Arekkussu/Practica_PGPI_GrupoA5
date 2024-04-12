document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const loginData = {
            correo: document.getElementById('floatingInput').value,
            contrasena: document.getElementById('floatingPassword').value
        };

        fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // Asume que la respuesta es JSON solo si la respuesta es exitosa
                } else {
                    throw new Error('Failed to log in'); // Lanza un error si la respuesta no es 200 OK
                }
            })
            .then(data => {
                console.log('Success:', data);
                window.location.href = '/inicio';
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('Hubo un error al iniciar sesión. Por favor, inténtalo de nuevo.'); // Muestra un mensaje de error al usuario
            });
    });

});