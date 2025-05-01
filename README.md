# Java EE Session Beans Application

## 1. Introduction to EJB and Session Beans

### Enterprise JavaBeans (EJB)

- EJBs are server-side components that encapsulate **business logic** of an enterprise application.
- They run in an **EJB container** and are part of the **Java EE (Jakarta EE)** platform.

### Types of Session Beans

- **Stateless**: No conversational state between client and bean.
- **Stateful**: Maintains state across method calls and transactions.
- **Singleton**: Single instance shared across the application.

---

## 2. Project Overview

The provided ZIP contains **four example modules**, each demonstrating a type of session bean:

- `Stateless-Session-Bean`
- `Stateful-Session-Bean`
- `Stateful-Session-Bean-2`
- `Singleton-Session-Bean`

### Each project has two submodules:

- **EJBModule**  
  Contains:
  - Bean interfaces (`@Remote`, `@Local`)
  - Bean implementation classes (`@Stateless`, `@Stateful`, `@Singleton`)

- **WebModule**  
  Contains:
  - `Home.java` (Servlet client)
  - `index.jsp` (Web UI)

---

## 3. Common Structure

### Packages

- `remote` or `local`: Define interfaces annotated with `@Remote` or `@Local`.
- `bean`: Implements those interfaces with appropriate annotations.
- `client`: Web module using servlets and JSP.

---

# Example Code Structure

### 1. Stateless Session Bean

**Interface: AppSettings (Local):**
```java
package com.deltacodex.ee.ejb.remote;
import jakarta.ejb.Local;

@Local
public interface AppSettings {
    String getAppName();        // Returns application name
    String getAppVersion();     // Returns version string
    String getAppDescription(); // Returns description
}
```
`@Local`: bean is accessed within the same application container.

**Bean Implementation: `AppSettingsBean`**
```java
package com.deltacodex.ee.ejb.impl;
import com.deltacodex.ee.ejb.remote.AppSettings;
import jakarta.ejb.Stateless;

@Stateless                      // Declares a stateless session bean
public class AppSettingsBean implements AppSettings {
    @Override
    public String getAppName() {
        System.out.println(this);    // Logs bean instance
        return "EJB Module";
    }
    ...
}
```
- `@Stateless`: container can pool instances; no client-specific state.
- `System.out.println(this)`: prints bean identity; for demonstration.
  
**Client Servlet: `Home.java`**
```java
package com.deltacodex.ee.web;
import com.deltacodex.ee.ejb.remote.AppSettings;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class Home extends HttpServlet {
    @EJB                       // Injects a local bean
    private AppSettings settings;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().println("Name: " + settings.getAppName());
        ...
    }
}
```
- `@EJB`: CDI injection of EJB.
- `doGet()`: invokes bean methods and writes HTML.
  
> Flow: HTTP request → servlet `Home` → injected `AppSettingsBean` → business methods → response.

---

### 2. Stateful Session Bean

**Interface: ShoppingCart (Remote)**
```java
package com.deltacodex.ee.ejb.remote;
import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface ShoppingCart {
    void addItem(String item);
    List<String> getItems();
}
```
`@Remote`: clients may reside in different JVM.

**Bean: ShoppingCartBean**
```java
package com.deltacodex.ee.ejb.impl;
import com.deltacodex.ee.ejb.remote.ShoppingCart;
import jakarta.ejb.Stateful;
import java.util.ArrayList;

@Stateful                      // Declares a stateful bean
public class ShoppingCartBean implements ShoppingCart {
    private List<String> items = new ArrayList<>();

    @Override
    public void addItem(String item) {
        items.add(item);         // Maintains client-specific state
    }

    @Override
    public List<String> getItems() {
        return items;
    }
}
```
- `@Stateful`: container retains instance per client; supports passivation.

**Client Lookup: `Home.java`**
```java
InitialContext ctx = new InitialContext();
// JNDI name from portableJNDI.txt
String jndi = "java:global/.../ShoppingCartBean!com.deltacodex.ee.ejb.remote.ShoppingCart";
ShoppingCart cart = (ShoppingCart) ctx.lookup(jndi);
cart.addItem("Apple");
List<String> items = cart.getItems();
```
> Portable JNDI names listed in `portableJNDI.txt` for lookup

**Advanced Stateful Example (Stateful-Session-Bean-2)**

Similar to above;
- demonstrates `@Remove` and lifecycle events (`@PostConstruct`, `@PreDestroy`).
- `@Remove` marks method to destroy bean instance.
- `@Stateful(passivationCapable=true)`: allows passivation to disk.

---

### 3. Singleton Session Bean

**Interface: Counter (Local)**
```java
@Local
public interface Counter {
    long increment();
    long getValue();
}
```
**Bean: CounterBean**
```java
@Singleton // Single instance in JVM
@Startup   // Eagerly instantiated
public class CounterBean implements Counter {
    private long count;

    @Lock(LockType.WRITE)
    public long increment() {
        return ++count;
    }

    @Lock(LockType.READ)
    public long getValue() {
        return count;
    }
}
```
- `@Singleton` ensures one shared instance.
- `@Startup`: instantiate at container startup.
- `@Lock`: controls concurrent access.
---

### 4. `index.jsp` (Common for All WebModules)

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session Bean Demo</title>
</head>
<body>
    <h2>Welcome to Java EE Session Bean Demo</h2>
    <a href="Home">Call Session Bean</a>
</body>
</html>
```
---
## 4. Key Takeaways

- **Stateless beans**: no client state, pooled.
- **Stateful beans**: maintain conversational state, passivation.
- **Singleton beans**: shared, concurrency-managed.
- **Annotations** simplify config; JNDI names or injection points for lookup.

