---
goal: 'Integrar MongoDB para a persistencia de datos do xogo Simon Dice'
version: '1.0'
date_created: '2026-01-14'
last_updated: '2026-01-14'
owner: 'Gemini'
status: 'Planned'
tags: ['feature', 'database', 'mongodb', 'refactor']
---

# Introdución

![Estado: Planned](https://img.shields.io/badge/status-Planned-blue)

O obxectivo deste plan é refactorizar a lóxica de persistencia de datos da aplicación "Simon Dice". Actualmente, os datos como a puntuación récord (`Record.kt`) son temporais. Este plan detalla os pasos para implementar unha solución de base de datos robusta utilizando MongoDB, e ofrece unha comparativa coa alternativa nativa de Android, Room.

## 1. Requisitos e Restricións

- **REQ-001**: A aplicación debe gardar a puntuación máxima (récord) de forma persistente entre sesións.
- **REQ-002**: A data na que se conseguiu o récord debe ser gardada xunto coa puntuación.
- **REQ-003**: A solución debe ser escalable por se no futuro se quere gardar un historial de puntuacións por usuario.
- **CON-001**: A implementación debe realizarse na rama `feature/MongoDB`.
- **GUD-001**: Débese avaliar a idoneidade de MongoDB fronte a Room para este caso de uso específico.

## 2. Análise de Alternativas: MongoDB vs. Room

Aquí se expoñen os pros e contras de cada tecnoloxía para este proxecto.

### Room (Opción Recomendada)

Room é a capa de abstracción sobre SQLite recomendada oficialmente por Google para Android.

- **Pros**:
    - **Nativa e Optimizada para Android**: Integración perfecta co ecosistema de Android Jetpack (ViewModels, LiveData, Coroutines).
    - **Offline-First**: Funciona sen conexión a internet por defecto, ideal para unha aplicación móbil.
    - **Robusta e Segura**: Ofrece comprobacións de sintaxe SQL en tempo de compilación, evitando erros en tempo de execución.
    - **Menor Curva de Aprendizaxe**: Se xa se coñece SQL, a adaptación é rápida. A xestión de dependencias é máis sinxela.
- **Contras**:
    - **Estrutura Ríxida**: Require un esquema de datos ben definido (táboas e columnas), o que a fai menos flexible se os datos cambian moito.

### MongoDB (Opción Solicitada)

MongoDB é unha base de datos NoSQL orientada a documentos. Para usala en Android, normalmente se emprega a través dun servizo na nube como MongoDB Atlas.

- **Pros**:
    - **Esquema Flexible**: Moi bo para datos non estruturados ou semi-estruturados. Poderíase gardar cada partida como un documento JSON complexo.
    - **Escalabilidade Horizontal**: Ideal para sistemas distribuídos con grandes volumes de datos (aínda que é esaxerado para este proxecto).
- **Contras**:
    - **Complexidade e Dependencia de Rede**: Require xestionar unha conexión a un servidor remoto (MongoDB Atlas). A app necesitaría internet para gardar ou ler o récord.
    - **Overkill (Excesivo) para o Proxecto**: As funcionalidades de MongoDB superan con creces as necesidades dun simple xogo de "Simon Dice". Introduce complexidade innecesaria (autenticación, xestión de latencia de rede, custos do servizo na nube).
    - **Consumo de Batería e Datos**: As comunicacións constantes a través da rede teñen un impacto no dispositivo do usuario.

**Conclusión da Análise**: Aínda que a tarefa é implementar MongoDB, **a recomendación profesional para esta aplicación é usar Room**. É máis sinxelo, eficiente, e se axusta perfectamente ao caso de uso de gardar unha puntuación localmente. MongoDB sería unha boa opción para unha aplicación con compoñente social (ranking global, perfís de usuario na nube, etc.).

## 3. Pasos de Implementación (MongoDB Atlas)

### Fase 1: Configuración do Proxecto e Dependencias

- GOAL-001: Preparar o entorno para a integración con MongoDB.

| Tarefa   | Descrición                                                                                                                               | Completado | Data |
|----------|------------------------------------------------------------------------------------------------------------------------------------------|------------|------|
| TASK-001 | Engadir a dependencia do driver de MongoDB para Kotlin no `build.gradle.kts` do módulo `app`.                                             |            |      |
| TASK-002 | Crear unha conta en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) e configurar un clúster gratuíto (M0).                            |            |      |
| TASK-003 | Obter a cadea de conexión (connection string) de Atlas e gardala de forma segura (p.ex., en `local.properties`, non no código).             |            |      |
| TASK-004 | Crear un obxecto singleton en Kotlin para inicializar e xestionar a conexión coa base de datos para que non se recree constantemente.      |            |      |

### Fase 2: Modelado e Lóxica de Acceso a Datos

- GOAL-002: Crear as estruturas de datos e as funcións para interactuar coa base de datos.

| Tarefa   | Descrición                                                                                                                                  | Completado | Data |
|----------|---------------------------------------------------------------------------------------------------------------------------------------------|------------|------|
| TASK-005 | Crear unha `data class` `RecordData(val score: Int, val date: Long)` para representar o documento que se gardará en MongoDB.                 |            |      |
| TASK-006 | Eliminar o `object Record` actual, xa que a súa funcionalidade será substituída pola base de datos.                                          |            |      |
| TASK-007 | Crear unha clase `RecordRepository` que conteña as funcións `suspend` para interactuar con MongoDB (p.ex., `getRecord()`, `saveRecord(record: RecordData)`). |            |      |
| TASK-008 | Implementar a lóxica en `RecordRepository` para conectarse á colección de "records" e realizar as operacións CRUD (neste caso, `find` e `updateOne`). |   |      |

### Fase 3: Integración coa Lóxica da Aplicación

- GOAL-003: Conectar a nova capa de datos co `ViewModel` e a UI.

| Tarefa   | Descrición                                                                                                                              | Completado | Data |
|----------|-----------------------------------------------------------------------------------------------------------------------------------------|------------|------|
| TASK-009 | Modificar `MyViewModel.kt` para inxectar ou obter unha instancia de `RecordRepository`.                                                  |            |      |
| TASK-010 | No `ViewModel`, crear funcións para cargar o récord inicial desde o repositorio ao iniciar a app. Usar `viewModelScope` para as corutinas. |            |      |
| TASK-011 | Cando o xogador perda, comparar a súa puntuación co récord actual obtido do repositorio. Se é maior, chamar a `repository.saveRecord()`.  |            |      |
| TASK-012 | Asegurarse de que a UI mostre correctamente o récord obtido da base de datos.                                                             |            |      |

## 4. Ficheiros Afectados

- **FILE-001**: `app/build.gradle.kts` (engadir dependencias).
- **FILE-002**: `app/src/main/java/gz/dam/simondicejorgeoliver/Utility/Instancia/Record.kt` (será eliminado/modificado).
- **FILE-003**: `app/src/main/java/gz/dam/simondicejorgeoliver/KotlinBase/MyViewModel.kt` (integrar o repositorio).
- **FILE-004**: `app/src/main/java/gz/dam/simondicejorgeoliver/KotlinBase/UI.kt` (verificar que a UI consume os datos do ViewModel).
- **FILE-005**: Novo ficheiro: `app/src/main/java/gz/dam/simondicejorgeoliver/Utility/Mongo/RecordRepository.kt` (ou similar).

## 5. Probas (Testing)

- **TEST-001**: Probar que o récord se carga correctamente ao iniciar a aplicación.
- **TEST-002**: Probar que un novo récord se garda correctamente na base de datos tras superar a puntuación anterior.
- **TEST-003**: Probar que o récord persiste se a aplicación se pecha e se volve a abrir.
- **TEST-004**: Probar o comportamento da app cando non hai conexión a internet. Debería manexar o erro de forma elegante.
