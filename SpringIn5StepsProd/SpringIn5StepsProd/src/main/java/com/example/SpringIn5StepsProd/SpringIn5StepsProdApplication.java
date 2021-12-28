package com.example.SpringIn5StepsProd;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringIn5StepsProdApplication {
	// what are the different beans that Spring has to manage
	// what are the dependencies for the bean
	// where to search for beans

	public static void main(String[] args) {

		//ApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsProdApplication.class, args);
		//BinarySearchImpl binarySearch =
			//	applicationContext.getBean(BinarySearchImpl.class);

		// BinarySearchImpl binarySearchImp = new BinarySearchImpl(new BubbleSortAlgorithm());
		// Spring manages the dependencies and injects the dependencies when mneeded
		// Managed the lifecycle of beans

		ApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsProdApplication.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		int result =
				binarySearch.binarySearch(new int[] { 12, 4, 6 }, 3);
		System.out.println(result);

	}

}
