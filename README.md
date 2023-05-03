# best parser

The [main class](https://github.com/jxxe/best-parser/blob/master/src/Main.java) has a basic example that you can run and tweak. The HTML parser doesn't support a lot of things, but notably self-closing tags (`<hr>` → `<hr></hr>`). It probably can't parse any old website you find online, but it might give you a nice error message. Might.

### Supported CSS Selectors
| Symbol | Name             |
|--------|------------------|
| `>`    | Child            |
| `.`    | Class            |
| `#`    | ID               |
| `~`    | Sibling          |
| `+`    | Adjacent Sibling |
| `*`    | Wildcard         |
|        | Tags             |
|        | Descendant       |

<sub>best parser</sub>
