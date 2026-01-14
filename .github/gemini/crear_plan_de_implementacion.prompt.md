### Directiva para Crear un Plan de Implementación

#### Directiva Primaria
O teu obxectivo é crear un novo ficheiro de plan de implementación. O teu resultado debe ser lexible por máquina, determinista e estruturado para a execución autónoma por parte de outros sistemas de IA ou humanos. Usa o idioma "Galego" ou "Español" para redactar o plan.

#### Requisitos Core
- Xerar plans de implementación que sexan totalmente executables.
- Usar linguaxe determinista sen ambigüidade.
- Estruturar todo o contido para a análise e execución automatizada.
- Garantir que o plan sexa autosuficiente.

#### Especificacións do Ficheiro de Saída
- **Directorio:** `/plan/`
- **Convención de nomes:** `[proposito]-[compoñente]-[version].md`
- **Prefixos de propósito:** `upgrade|refactor|feature|data|infrastructure|process|architecture|design`

---

### Estrutura de Modelo (Template) Obrigatoria

```markdown
---
goal: [Título conciso que describa o obxectivo do plan]
version: [Opcional: p.ex., 1.0, Data]
date_created: [AAAA-MM-DD]
last_updated: [Opcional: AAAA-MM-DD]
owner: [Opcional: Equipo/Individuo responsable]
status: 'Planned' # Completed|In progress|Planned|Deprecated|On Hold
tags: [Opcional: Lista de etiquetas, p.ex., feature, upgrade]
---

# Introdución

![Estado: <status>](https://img.shields.io/badge/status-<status>-<blue>)

[Unha introdución curta e concisa sobre o plan e o obxectivo que pretende alcanzar.]

## 1. Requisitos e Restricións

- **REQ-001**: 
- **SEC-001**: 
- **CON-001**: 

## 2. Pasos de Implementación

### Fase de Implementación 1

- GOAL-001: [Describe o obxectivo desta fase]

| Tarefa | Descrición | Completado | Data |
|------|-------------|-----------|------|
| TASK-001 |             |           |      |

## 3. Alternativas

- **ALT-001**: 

## 4. Dependencias

- **DEP-001**: 

## 5. Ficheiros

- **FILE-001**: 

## 6. Probas (Testing)

- **TEST-001**: 

## 7. Riscos e Asuncións

- **RISK-001**: 
- **ASSUMPTION-001**: 

## 8. Especificacións Relacionadas / Lecturas Adicionais

- [Ligazón á documentación relevante]
```
