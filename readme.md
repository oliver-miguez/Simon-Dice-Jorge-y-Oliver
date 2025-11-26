# Simón No Dice

## Oliver Miguez Alonso

## Jorge Duran Cruz

### Explicación:

Se basa en el clásico juego del simón dice, principalmente centrandonos en el uso de corrutinas y el modelo MVVM,
completamente desarrollado en ***Kotlin*** y ***JetPack Compose*** de Android Studio.


### Funcionamiento

![1.jpeg](imgs/1.jpeg)
Cuando inicias la aplicación
![2.jpeg](imgs/2.jpeg)
Cuando juegas la aplicación
![3.jpeg](imgs/3.jpeg)
Cuando pierdes en la aplicación
![4.jpeg](imgs/4.jpeg)
Cuando vuelves a jugar en la aplicación

### Diagrama de estados:

```mermaid
    stateDiagram-v2
    [*] --> Inicio
    Inicio --> Generando:numeroRandom()
    Generando --> Adivinando:actualizarNumero()
    Adivinando --> Generando:correcionOpcionElegido()
    Adivinando --> Inicio:derrota()
```

