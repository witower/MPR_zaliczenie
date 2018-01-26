package jdbcdemo.dao;

import jdbcdemo.domain.Car;
import jdbcdemo.domain.Cellphone;
import jdbcdemo.domain.Person;

public interface RepositoryCatalog {

	Repository<Person> people();

	Repository<Car> cars();
	
	Repository<Cellphone> cellphones();

	void saveChanges();

}