/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc2;

/**
 *
 * @author lustin
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;


public class TrackerDynamicProxy
{
    
    private final JSONRPC2Session         client;
    //private final TypeConverterFactory typeConverterFactory;
    private boolean                    objectMethodLocal;
    
    /**
     * Creates a new instance.
     * 
     * @param client
     *            A fully configured XML-RPC client, which is used internally to
     *            perform XML-RPC calls.
     * @param typeConverterFactory
     *            Creates instances of {@link TypeConverterFactory}, which are
     *            used to transform the result object in its target
     *            representation.
     */
//    public TrackerDynamicProxy( XmlRpcClient client,
//            TypeConverterFactory typeConverterFactory )
//    {
//        this.typeConverterFactory = typeConverterFactory;
//        this.client = client;
//    }
    
    /**
     * Creates a new instance. Shortcut for
     * 
     * <pre>
     * new ClientFactory( pClient, new TypeConverterFactoryImpl() );
     * </pre>
     * 
     * @param client
     *            A fully configured XML-RPC client, which is used internally to
     *            perform XML-RPC calls.
     * @see TypeConverterFactoryImpl
     */
    public TrackerDynamicProxy(JSONRPC2Session client )
    {
        //this( client, new TypeConverterFactoryImpl() );
        this.client = client;
    }
    
    
    
    /**
     * Returns the factories client.
     */
    public JSONRPC2Session getClient()
    {
        return client;
    }
    
    /**
     * Returns, whether a method declared by the {@link Object Object class} is
     * performed by the local object, rather than by the server. Defaults to
     * true.
     */
    public boolean isObjectMethodLocal()
    {
        return objectMethodLocal;
    }
    
    /**
     * Sets, whether a method declared by the {@link Object Object class} is
     * performed by the local object, rather than by the server. Defaults to
     * true.
     */
    public void setObjectMethodLocal( boolean objectMethodLocal )
    {
        this.objectMethodLocal = objectMethodLocal;
    }
    
    /**
     * Creates an object, which is implementing the given interface. The objects
     * methods are internally calling an XML-RPC server by using the factories
     * client.
     */
    public Object newInstance( Class<?> clazz )
    {
        return newInstance( Thread.currentThread().getContextClassLoader(),
                clazz );
        
    }
    
    
    /**
     * Creates an object, which is implementing the given interface. The objects
     * methods are internally calling an XML-RPC server by using the factories
     * client.
     */
    public Object newInstance( ClassLoader classLoader, final Class<?> clazz )
    {
        return Proxy.newProxyInstance( classLoader, new Class[] { clazz },
                new InvocationHandler() {
                    public Object invoke( Object proxy, Method method,
                            Object[] args ) throws Throwable
                    {
                        if ( isObjectMethodLocal()
                                && method.getDeclaringClass().equals(
                                        Object.class ) ) { return method.invoke( proxy, args ); }
                    
                        String _classname = clazz.getName().replaceFirst(clazz.getPackage().getName()+".", "").toLowerCase();
                        
                        _classname = _classname.replace("$", "."); //dirty hack TODO check
                        
                        String methodName = _classname
                                + "." + method.getName();
                        
                        Object result = null;
                        
                        String id = "";
                        
                        JSONRPC2Request request = new JSONRPC2Request(methodName, Arrays.asList(args), id);
                        client.disableStrictParsing(true);
                        
                        try{
                        	JSONRPC2Response response = client.send(request);
                            result = response.getResult();
                            
                            if(response.getError() != null){
                            	throw new TracException(response.getError().getMessage());
                            }
                        }catch(JSONRPC2SessionException e){
                        	e.printStackTrace();
                        }
                        return result;
                    }
                } );
    }
}
