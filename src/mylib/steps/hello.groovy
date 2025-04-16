package mylib.steps

class hello {

    static Closure call() {
        return { Map ctx ->
            echo "👋 Hello ${ctx.name}"
        }
    }

}
