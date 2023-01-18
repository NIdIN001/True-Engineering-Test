# Тестовое задание в True Engineering

Задача: Реализовать методы интерфейса GraphService, используя в качестве хранилища данных файл.

В репозитории представлено решение, сериализующее граф в текстовом формате. Имплементация не имеет сторонних
зависимостей.

Формат сериализации:

- graphId [{nodeId,nodeValue},...] [{edgeFrom,edgeTo},...]

Была идея реализовать сериализацию графа в бинарном формате, что было бы эффективнее в плане занимаемой памяти, но решил
отказаться от данной идеи ввиду больших трудозатрат на реализацацию (работа с байтами, битовые сдвиги и т.д.).

Ниже представлен нереализованный формат сериализации графа в бинарном виде:

#### Header:

- [4 bytes] Header Size
- [4 bytes] Serialisation version

#### Body:

- [4 bytes] Graph Structure Size
- [4 bytes] Graph Id
- [4 bytes] Graph Nodes Count
- {[4 bytes] Id, [4 bytes] value} — Repeated Nodes count times
- [4 bytes] Graph Edges Count
- {[4 bytes] edgeFrom, [4 bytes] edgeTo} — Repeated Edges count times