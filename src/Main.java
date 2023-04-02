import dom.Dom;
import dom.HtmlParser;
import rules.Rule;
import rules.RuleParser;

public class Main {

    public static void main(String[] args) {
        Dom dom = HtmlParser.fromString("""
            <html lang="en-US">
                <body>
                    <div class="parent">
                        <p class="child">Hello, world!</p>
                        <div></div>
                    </div>
                </body>
            </html>
        """);

        Rule rule = RuleParser.fromString("div.parent");

        var results = rule.enforce(dom);

        System.out.println(results);
    }

}