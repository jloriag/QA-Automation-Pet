from locust import HttpUser, task, between

class PetstoreUser(HttpUser):
    wait_time = between(1, 3)  # Espera entre 1 y 3 segundos entre tareas

    @task
    def get_pet_by_id(self):
        """Simula la solicitud para obtener una mascota por ID."""
        pet_id = 1  # Cambia el ID según tus pruebas
        self.client.get(f"/pet/{pet_id}")

    @task
    def create_pet(self):
       """Simula la creación de una nueva mascota.""" 
       self.client.post("/pet", json={
         "id": 123,
           "name": "New Pet",
         "status": "available"
       })


