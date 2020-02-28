<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <xsl:for-each select="messages/message">
                    <xsl:if test="userFrom/id/text() = ../currentUser/text()">
                        <li style="
                        list-style-type: none;
                        margin-bottom: 3px;
                        padding: 5px 10px;
                        clear: both;
                        border-radius: 10px 10px 2px 2px ;
                        float: left;
                        border-bottom-left-radius: 10px;">
                            <xsl:apply-templates select="messageContent"/>
                        </li>
                        <br/>
                    </xsl:if>
                    <xsl:if test="userFrom/id/text() != ../currentUser/text()">
                        <li style="
                        list-style-type: none;
                        margin-bottom: 3px;
                        padding: 5px 10px;
                        clear: both;
                        border-radius: 10px 10px 2px 2px ;
                        float: right;
                        border-bottom-right-radius: 10px;">
                            <xsl:apply-templates select="messageContent"/>
                        </li>
                        <br/>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="messageContent">
                <div>
                    <xsl:attribute name="style">
                        <xsl:text>font-fill:</xsl:text>
                        <xsl:value-of select="../style/font-color/text()" />
                        <xsl:text>;</xsl:text>

                        <xsl:text>font-size:</xsl:text>
                        <xsl:value-of select="../style/font-size/text()" />
                        <xsl:text>px;</xsl:text>

                        <xsl:text>font-weight:</xsl:text>
                        <xsl:value-of select="../style/font-weight/text()" />
                        <xsl:text>;</xsl:text>

                        <xsl:text>font-family:</xsl:text>
                        <xsl:value-of select="../style/font-family/text()" />
                        <xsl:text>;</xsl:text>

                        <xsl:text>background-color:</xsl:text>
                        <xsl:value-of select="../style/fill-background/text()" />
                        <xsl:text>;</xsl:text>

                        <xsl:text>border-radius: 15px 50px; text-align: center; padding: 20px</xsl:text>
                    </xsl:attribute>
                    <xsl:value-of select="."/>
                </div>
    </xsl:template>
</xsl:stylesheet>