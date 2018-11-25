@XmlSchema(
        namespace = "http://fantasysports.yahooapis.com/fantasy/v2/base.rng",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix="yahoo", namespaceURI="http://www.yahooapis.com/v1/base.rng")
        }
)
package com.pldfodb.controller.model;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;