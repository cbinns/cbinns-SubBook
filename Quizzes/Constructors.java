// compile: javac Constructors.java
// run as  java Consructors
// file has to have the same name as the main class you want to execute?

class A {
    public A( int initValue ) {
        System.out.println( "1" );
    }
    public A() {
        System.out.println( "2" );
    }
}

class B extends A {
    protected int value;
    public B( int initValue ) {
        System.out.println( "3" );
        value = initValue;
    }
    public B() {
        this( 1 );
        System.out.println( "4" );
    }
}

class C extends B {
    public C( int initValue ) {
        super( initValue );
        System.out.println( "5" );
    }
    public C() {
        System.out.println( "6" );
    }
}

public class Constructors {
    public static void main( String[] args) {
        C c = new C();
    }
}