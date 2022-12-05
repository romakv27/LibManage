package ru.study.library.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.study.library.api.DataProviderCSV;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.Book;

public class BookConverter extends AbstractBeanField<Book, String>{
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] str = s.split("-");
        TypeOfBook typeOfBook = TypeOfBook.valueOf(str[0]);
        return new DataProviderCSV().getBookByTypeAndId(typeOfBook, Long.parseLong(str[1])).orElseThrow(() -> {
            throw new UnsupportedOperationException("");
        });
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return super.convertToWrite(String.format("%s-%d", ((Book) value).getTypeOfBook().name(), ((Book) value).getId()));
    }
}
