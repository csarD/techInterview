package cuentas;

import com.intuit.karate.junit5.Karate;

class CuentasRunner {

    @Karate.Test
    Karate testUsers() {
        return Karate.run("users").relativeTo(getClass());
    }

}