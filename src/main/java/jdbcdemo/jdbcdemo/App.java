package jdbcdemo.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import jdbcdemo.dao.*;
import jdbcdemo.dao.uow.UnitOfWork;
import jdbcdemo.domain.*;

public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    System.out.println( "Inicjalizacja\n" );
    	RepositoryCatalog workdb = new JdbcCatalogFactory().HsqlDbWorkDb();
    	
    	System.out.println( "Tworzenie tabel\n" );
    	workdb.people().createTable();
    	workdb.cars().createTable();
    	workdb.cellphones().createTable();

    System.out.println( "Dodawanie rekordów\n" );
    	
    	workdb.people().add(new Person("Jan", "Kowalski", 30)); 
    	workdb.people().add(new Person("Ola", "Nowak", 25)); 
    	workdb.people().add(new Person("Henryk", "Bogacki", 77)); 
    	
    	workdb.cars().add(new Car("VW", "GWE 6666"));
    	workdb.cars().add(new Car("Ford", "GKA 0101"));
    	workdb.cars().add(new Car("Opel", "GDA 9999"));
    	
    	workdb.cellphones().add(new Cellphone("Sony", "Xperia Z1"));
    	workdb.cellphones().add(new Cellphone("Samsung", "Galaxy 99"));
    	workdb.cellphones().add(new Cellphone("Xiaomi", "Redmi Note"));
    	
    System.out.println( "Zapisywanie zmian\n" );
    	workdb.saveChanges();
    	
    System.out.println( "Wyświetlanie tabel\n" );
    	
    	System.out.println( "\t Ludzie:" );
    	System.out.println(workdb.people().toString());
    	
    	System.out.println( "\t Auta:" );
    	System.out.println(workdb.cars().toString());
    	
    	System.out.println( "\t Telefony:" );
    	System.out.println(workdb.cellphones().toString());

    System.out.println( "Edycja rekordów\n" );
    	Person personToUpdate = new Person("Jasiek", "Kowal***", 99);
    	personToUpdate.setId(0);
    	workdb.people().update(personToUpdate);
    	workdb.saveChanges();

    System.out.println( "Wyświetlanie tabel po zmianach\n" );
    	List<Person> people = workdb.people().getAll();
    	
    	System.out.println( "\t Ludzie:" );
    	for (Person p : people) {
    		System.out.println(p.getId() + " " 
    				+ p.getName() + " " 
    				+ p.getSurname() + " " 
    				+ p.getAge());
    	}
    	
    System.out.println( "Usuwanie rekordów\n" );
    	Person personToDelete = new Person();
    	personToDelete.setId(1);
    	workdb.people().delete(personToDelete);
    	workdb.saveChanges();

    System.out.println( "Wyświetlanie tabeli po zmianie\n" );
    	System.out.println( "\t Ludzie:" );
    	System.out.println(workdb.people().toString());
    	
    System.out.println( "Zakończenie" );
    	
    }
}
