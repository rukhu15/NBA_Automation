package framework.utility.dataParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Prateek Sethi
 *
 *
 */

public class DataListBuilder<T> extends DataBuilder<List<T>> {

	@SuppressWarnings("unchecked")
	public DataListBuilder(Class<T> klass) {
		super((Class<List<T>>) klass);
	}
	
	@Override
	public Iterator<Object[]> from(String dataNotation) {
		return wrapList(from(dataNotation, true));
	}
	
	@SuppressWarnings("unchecked")
	public List<T> from(String dataNotation, boolean genericType) {

		List<Map<String, String>> all_rows = 
			dataSource.fetchMapList(dataNotation);

		

		List<T> data_list = new ArrayList<T>();

		if (!(all_rows == null || all_rows.size() == 0)) {

			for (Iterator<Map<String, String>> iterator = all_rows.iterator(); 
			iterator.hasNext();) {
				Map<String,String> map = (Map<String,String>) iterator.next();
				data_list.add((T) this.buildIt(map)); 
			}
		}	
		return data_list;
	}
	
	private Iterator<Object[]> wrapList(List<T> instances) {
		List<Object[]> objects = new ArrayList<Object[]>();
		
		for (Iterator<T> iterator = instances.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			Object objectAry[] = { object };
			objects.add(objectAry);
		}
		
		return objects.listIterator();
	}
}
