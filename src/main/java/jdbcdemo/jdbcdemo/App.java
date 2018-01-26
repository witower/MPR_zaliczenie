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
    	System.out.println( "Inicjalizacja" );
    	RepositoryCatalog workdb = new JdbcCatalogFactory().HsqlDbWorkDb();
    	
    	System.out.println( "Tworzenie tabel" );
    	workdb.people().createTable();
    	workdb.cars().createTable();

    	System.out.println( "Dodawanie rekordów" );
    	workdb.people().add(new Person("Jan", "Kowalski", 30));  
    	workdb.cars().add(new Car("vw", "GWE 6666"));
    	
    	System.out.println( "Zapisywanie zmian" );
    	workdb.saveChanges();
    	
    	System.out.println( "Wyświetlanie tabel" );
    	
    	System.out.println( "\t Ludzie:" );
    	System.out.println(workdb.people().toString());
    	
    	System.out.println( "\t Auta:" );
    	System.out.println(workdb.cars().toString());
    	
    	System.out.println( "\t Telefony:" );
    	System.out.println(workdb.cellphones().toString());

    	System.out.println( "Edycja rekordów" );
    	Person personToUpdate = new Person("Jasiek", "Kowal***", 99);
    	personToUpdate.setId(0);
    	workdb.people().update(personToUpdate);
    	workdb.saveChanges();

    	System.out.println( "Wyświetlanie tabel po zmianach" );
    	List<Person> people = workdb.people().getAll();
    	
    	System.out.println( "\t Ludzie:" );
    	for (Person p : people) {
    		System.out.println(p.getId() + " " 
    				+ p.getName() + " " 
    				+ p.getSurname() + " " 
    				+ p.getAge());
    	}
    	
    	
    	
    	System.out.println( "Usuwanie rekordów" );
    	Person personToDelete = new Person();
    	personToDelete.setId(1);
    	workdb.people().delete(personToDelete);
    	workdb.saveChanges();

    	System.out.println( "Wyświetlanie tabeli po zmianie" );
    	System.out.println( "\t Ludzie:" );
    	System.out.println(workdb.people().toString());
    	
    	System.out.println( "Zakończenie" );
    	
    }
}
