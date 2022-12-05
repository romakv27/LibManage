package ru.study.library.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.study.library.api.DataProviderCSV;
import ru.study.library.model.User;

public class UserConverter extends AbstractBeanField<User, Long> {
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return new DataProviderCSV().getUserById(Long.parseLong(s)).orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format("User is %d not exists", Long.parseLong(s)));
        });
    }

    @Override
    protected String convertToWrite(Object value) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        if (new DataProviderCSV().getUserById(((User) value).getId()).isEmpty()) {
            throw new UnsupportedOperationException("User is not exists");
        }
        return super.convertToWrite(((User) value).getId());
    }
}
