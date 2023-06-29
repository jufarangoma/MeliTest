# MeliTest

Prueba técnica de MercadoLibre

Estructura del proyecto

- data: Todas las implementaciones a los repositorios, objetos DTO y endpoints
- domain: Entidades, repositorios y excepciones de dominio
- presentation: Actividad, fragmentos, adaptadores y viewmodels
- di: Modules de inyección
- utils: Extensiones

Librerias utilizadas

- Retrofit2
- Hilt
- Navigation
- Glide
- Mockk
- Material
- JaCoCo

## Arquitectura utilizada

<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/arch.png" width="240" height="427"/>

Arriba se muestra el modelo que use para la arquitectura de la app, los componentes que se
encuentran con linea cortada no fueron incluidos en la prueba dado que no fueron necesarios, sin
embargo los incluí en el diseño ya que son componentes recurrentes con los que he trabajado.

- La capa de Presentación contiene los elementos visuales que se usaron en la app, además de los
  viewmodels
  encargados de mantener los datos que se requieran. En esta capa use el patron de arquitectura
  MVVM.

- La capa de Dominio contiene toda la logica de negocio, para esta app no fue necesario implementar
  casos de uso ya que la mayoria de logica fue implementada en las entidades

- La capa de Datos contiene todas las llamadas al Api de Mercado Libre, además de los modelos DTO y
  las implementaciones del repositorio

## Flujo de datos

Para el manejo asincrono de las peticiones use corrutinas y flow

## Pruebas Unitarias

Implemente pruebas unitarias para las tres capas y con ayuda de JaCoCo valide la cobertura de estas:

- Presentación: ViewModels

<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco1.png" />
<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco2.png" />

- Domain: Entidades

<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco3.png"/>

- Data: Repositorios, excepciones y Mappers

<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco4.png"/>
<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco5.png"/>
<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco6.png"/>
<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco7.png"/>
<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/jacoco8.png"/>

