package framework.utility.dataParam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Prateek Sethi
 *
 *
 */
public class DataBuilder<T> {
	private Class<T> klass;
	
	protected DataSource dataSource = null;
	
	public DataBuilder(Class<T> klass) {
		this.klass = klass;
		dataSource = new DataSource(klass);
	}

	public Iterator<Object[]> from(String dataNotation) {
		return wrapInstance(from(dataNotation, true));
	}
	
	public T from(String dataNotation, boolean genericType) {
		List<Map<String, String>> entity_data = 
			dataSource.fetchMapList(dataNotation);
		if (entity_data == null || entity_data.size() == 0) {
			System.out.println("entity_data data size is null");
		}

		Map<String, String> data_source = entity_data.get(0);
		
		return buildIt(data_source);
	}
	
	private Iterator<Object[]> wrapInstance(T instance) {
		Object data[] = { instance } ;
		
		List<Object[]> objects = new ArrayList<Object[]>();
		objects.add(data);
		
		return objects.listIterator();
	}
	
	protected T buildIt(Map<?, ?> map) {
		T instance = null;
		
		try {
			try {
				instance = klass.newInstance();
			} catch (Exception e1) {
				throw new Exception(e1);
			}
			Field[] fields = instance.getClass().getFields();
			for (Field field : fields) {
				try {
					if (map.containsKey(field.getName())) {
						field.set(instance, map.get(field.getName()));
					}
				} catch (Exception e) {
					throw new Exception(e);
				}
			}
		} catch (Exception e) {		
			throw new Error(e);
		}
		return instance;
	}
}