
О приложении:

В приложении есть два экрана. На первом экране размещаются две картинки, скачиваемые из сети.
При  клике по картинке происходит ее выделение цветом. При клике на floating action button
происходит переход на второй экран.На втором экране находится выбранное на первом экране изображение.
При нажатии на кнопку back происходит возврат на первый экран.
Для полноценной работы приложения необходимо наличие интернет соединения.

Реализация:

Экраны представлены фрагментами которые заменяют друг друга  в хостовой Activity.
Загрузка картинок происходит посредством получения  из сети (при помощи  HttpURLConnection connection)
и конвертирования в bitmap массива byte. Загрузка происходит в отдельном потоке посредством AsyncTask.
Изображения загружаются в Grid через наследник ArrayAdapter. Первоначальные данные (список ссылок)
 находятся в res/values/arrays . За счет setRetain(true) во фрагменте, осуществляется, своего рода,
кэширование скачанных данных в памяти
Выделение выбранного элемента, происходит в адаптере.

Возможные улучшения:
Организовать сохранение и использование в приложени загруженных изображений из сети в постоянной памяти.
Может отслеживать устаревание информации.
При увеличении числа отображаемых картинок нужно переделать механизм загрузки изображений через AsyncTask.
Возможно, сделать поочередную подгрузку изображений.
Входные данные получать в более удобном  виде - напрмер в виде json.

Улучшение кода:
Провести общий рефакторинг
Провести полное тестирование