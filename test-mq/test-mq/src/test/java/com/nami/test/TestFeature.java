package com.nami.test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import com.nami.twr.CusTry;

public class TestFeature {

	@Test
	public void test1() {
		//Arrays.asList(1,2,3,4,5).forEach(i->System.out.println(i));
		Integer i = 5;
		Consumer<Integer> consumer = (t)->System.out.println(t);
		consumer.accept(i);
	}
	
	@Test
	public void test2() {
		Instant instant = Instant.now();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone );
		System.out.println(localDateTime);
		
		Date from = Date.from(localDateTime.atZone(zone).toInstant());
		System.out.println(from);
	}
	
	@Test
	public void test3() {
		String arr[] = {"hello","world"};
		Stream<String> stream = Arrays.stream(arr);
		stream.forEach(System.out::println);
		
		
		
		String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        //Stream<String[]>
        Stream<String[]> temp = Arrays.stream(data);
        //Stream<String>, GOOD!
        Stream<String> stringStream = temp.flatMap(x -> Arrays.stream(x));
        //Stream<String> stream = stringStream.filter(x -> "a".equals(x.toString()));
        stringStream.forEach(System.out::println);
	}
	
	@Test
	public void test4() {
		Student obj1 = new Student();
        obj1.setName("mkyong");
        obj1.addBook("Java 8 in Action");
        obj1.addBook("Spring Boot in Action");
        obj1.addBook("Effective Java (2nd Edition)");

        Student obj2 = new Student();
        obj2.setName("zilap");
        obj2.addBook("Learning Python, 5th Edition");
        obj2.addBook("Effective Java (2nd Edition)");

        List<Student> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        

        List<String> collect =
                list.stream()
                        .map(x -> x.getBook())      //Stream<Set<String>>
                        .flatMap(x -> x.stream())   //Stream<String>
                        .distinct()
                        .collect(Collectors.toList());

        collect.forEach(x -> System.out.println(x));
	}
	
	public static void main1(String[] args) {
		/*String nullDef = Objects.toString(null, "other");
		System.out.println(nullDef);
		int hash = Objects.hash("s",1,1.2,1l);
		System.out.println(hash);
		int compare = Objects.compare(1, 2, (i1,i2)->{
			if (i1>i2) {
				return 1;
			}
			return 0;
		});
		System.out.println(compare);
		Object null1 = Objects.requireNonNull(11);
		System.out.println(null1);
		Objects.requireNonNull(null, "不能为空");
		Supplier<String> supplier = ()->new String("test is not null");
		String t = supplier.get();
		System.out.println(t);
		Objects.requireNonNull(null, supplier);*/
		
		/*try {
			testTryWR();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
		byte[] b = serializer.serialize("test");
		
		Object object = serializer.deserialize(b);
		System.out.println(object);
	}
	
	public static void main(String[] args) {
		String string = System.getProperty("path");
	}
	
	public static void testTryWR() throws Exception {
		try(CusTry t = new CusTry()){
			t.otherMethod();
		}
	}
}
