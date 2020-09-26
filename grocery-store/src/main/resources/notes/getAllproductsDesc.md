Optional data filtering <br/>
<small>To filter products, get request must be supplied with search parameter in URL. Every property from returned model can be filtered.<br/><br/>Syntax <br/><em>propertyName:operation:value</em></small><br/>
<small>Available operations: </small>
<ul>
    <li><small>: (equality)</small></li>
    <li><small>> (greater than)</small></li>
    <li><small>< (less than)</small></li>
</ul>
<br/>
Examples <br/>
<small>Get products that category id is 1 and price is greater than 50 <strong>(chained conditions using comma)</strong></small> <br/>
<small>/products?search=categoryId:1,price>50</small> <br/><br/>
<small>Get products with kg measurement</small> <br/>
<small>/products?search=measurement:kg </small>