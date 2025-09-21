@PetAll
Feature: APIS from Pet

  Como usuario de PetStore
  Quiero obtener la lista de Pets
  Para poder verificar los detalles de las mascotas

  @CP01_Pet @Pet
  Scenario: Obtener todas los pets disponibles exitosamente
    Given el actor establece el endpoint de pet
    When el actor realiza una solicitud pet GET
    Then el codigo de respuesta debe ser 200


  @CP02_Pet @Patch_Pet @Pet
  Scenario Outline: Crear un pet exitosamente
    Given el actor establece el endpoint de pet
    When el actor crea un pet con el "<id>" "<categoryId>" "<categoryName>" "<name>" "<photoUrls>" "<status>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | id   | categoryId | categoryName  | name   | photoUrls   | status    |
      | 2133 | 1          | Perro Pequeño | Maxter | Alguna Foto | available |

  @CP03_Pet @Put_Pet @Pet
  Scenario Outline: Actualizar datos de un pet exitosamente
    Given el actor establece el endpoint de pet
    And el actor crea un pet con el "<id>" "<categoryId>" "<categoryName>" "<name>" "<photoUrls>" "<status>"
    When el actor actualiza un pet con el nombre y categoria nombre "<categoryName>" "<name>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | id   | categoryId | categoryName | name        | photoUrls   | status    |
      | 2133 | 1          | Perro Grande | TheLuis1998 | Alguna Foto | available |



