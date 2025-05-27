# Shank Interpreter


A Java-based interpreter for the Shank programming language, implementing a complete lexer, parser, and interpreter with support for various data types, control structures, and built-in functions.


## Project Structure


```
Shank-Interpreter/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── shank/
│   │               ├── interpreter/    # Core interpreter components
│   │               │   ├── Lexer.java
│   │               │   ├── Parser.java
│   │               │   ├── Interpreter.java
│   │               │   ├── SemanticAnalysis.java
│   │               │   ├── Shank.java
│   │               │   └── Token.java
│   │               ├── builtin/        # Built-in function implementations
│   │               │   ├── Read.java
│   │               │   ├── Write.java
│   │               │   ├── Left.java
│   │               │   ├── Right.java
│   │               │   ├── Substring.java
│   │               │   ├── SquareRoot.java
│   │               │   ├── GetRandom.java
│   │               │   ├── IntegerToReal.java
│   │               │   └── RealToInteger.java
│   │               └── nodes/          # AST node implementations
│   └── test/
│       └── resources/      # Test files and sample programs
│           └── testfile1.txt
├── docs/
│   └── Shank Definition.pdf   # Language specification
└── .gitignore
```


## Key Features


1. **Language Features**
   - Multiple data types (Integer, Float, Boolean, String, Char)
   - Control structures (if/else, while, repeat, for)
   - Function definitions and calls
   - Variable declarations and assignments
   - Built-in functions


2. **Built-in Functions**
   - `read` - Input operations
   - `write` - Output operations
   - `Left` - String manipulation (left substring)
   - `Right` - String manipulation (right substring)
   - `Substring` - String operations
   - `squareRoot` - Mathematical operations
   - `getRandom` - Random number generation
   - `integerToReal` - Integer to real number conversion
   - `realToInteger` - Real number to integer conversion


3. **Implementation Components**
   - Lexical analysis
   - Syntax parsing
   - Semantic analysis
   - Abstract Syntax Tree (AST)
   - Runtime interpretation


## Building and Running


1. Compile the project:
   ```bash
   javac -d bin src/main/java/com/shank/**/*.java
   ```


2. Run the interpreter:
   ```bash
   java -cp bin com.shank.interpreter.Shank src/test/resources/testfile1.txt
   ```


## Example Usage


The project includes test files and sample programs demonstrating language features:


```shank
function main()
    variables
        integer x;
        string message;
    begin
        x := 10;
        message := "Hello, Shank!";
        write(message);
        write(x);
    end
```


See `src/test/resources/testfile1.txt` for more examples of language features and built-in functions.


## Documentation


For detailed language specifications and syntax rules, refer to the included `docs/Shank Definition.pdf` document.