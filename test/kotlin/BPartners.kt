import org.junit.Assert
import org.junit.Test
import org.compiere.crm.MBPartner
import org.idempiere.common.db.CConnection
import org.idempiere.common.db.Database
import org.idempiere.common.util.CLogger
import org.idempiere.common.util.DB
import org.idempiere.common.util.Env
import org.idempiere.common.util.Ini
import pg.org.compiere.db.DB_PostgreSQL

class CalculatorTest {
    @Test
    fun loading_saving_business_partner_work() {
        Ini.getIni().setClient(false)
        val log = CLogger.getCLogger(CalculatorTest::class.java)
        Ini.getIni().loadProperties(false)
        val properties = Ini.getIni().properties
        val db = Database()
        db.setDatabase(DB_PostgreSQL())
        DB.setDBTarget(CConnection.get(null))
        DB.isConnected()

        val ctx = Env.getCtx()
        ctx.setProperty(Env.AD_CLIENT_ID, "11" )
        Env.setContext(ctx, Env.AD_CLIENT_ID, "11" )

        val id = 118
        val partner = MBPartner.get( Env.getCtx(), id )

        Assert.assertEquals( id, partner.c_BPartner_ID)
        Assert.assertEquals( "JoeBlock", partner.value)
        Assert.assertEquals( "Joe Block", partner.name)

        val partner2 : org.compiere.crm.MBPartner = partner as MBPartner

        val newValue = "JoeBlock*"
        partner2.setValue( newValue )
        partner2.save()

        val partner3 = MBPartner.get( Env.getCtx(), id )

        Assert.assertEquals( id, partner3.c_BPartner_ID)
        Assert.assertEquals( newValue, partner3.value)
        Assert.assertEquals( "Joe Block", partner3.name)

        partner2.setValue( "JoeBlock" )
        partner2.save()
    }
}