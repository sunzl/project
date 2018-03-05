package com.nami.twr;

public class CusTry implements AutoCloseable{

	public void otherMethod() {
		System.out.println("cust method action ... ...");
	}
	@Override
	public void close() throws Exception {
		System.out.println("close ... ... ");
		throw new RuntimeException("throw a runtimeException .... ");
	}

}
