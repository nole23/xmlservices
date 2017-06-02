<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Company Information</h2>
                
                <xsl:for-each select="dokument/propisi" >
                
                	<xsl:value-of select="naziv_propisa" />
                </xsl:for-each>
                
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>