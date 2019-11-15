package com.stratio.crossdata.dataset.generator

import org.apache.spark.SparkContext
import org.apache.spark.sql.functions._
import java.sql.Date
import java.text.SimpleDateFormat
import scala.util.Random
import org.apache.spark.sql.SparkSession
import scala.collection.mutable.HashSet
import org.apache.spark.sql.Column



object DatioDSGenerator extends App {
  
  implicit val arguments = new AppArgsParser(args)
    
	val contracts = "contracts"
	val clients = "clients"

	val xd = SparkSession
	.builder()
	.appName("Crossdata Dataset Generator")   
	.master(arguments.sparkMaster())
	.config("spark.ui.enabled", "true")
	.getOrCreate()
	
	import com.stratio.crossdata.dataset.generator.Values._
	import com.stratio.crossdata.dataset.generator.Constant._
	import com.stratio.crossdata.dataset.generator.GeneratorUtils._

	def create_data_contracts(rowsLoop: Int, loops: Int) {
		for (x <- 0 until loops) {
			val lim_inf=1+(x*rowsLoop)
			val lim_sup=1+((x+1)*rowsLoop)
			val number_contracts = random.nextInt(2) + 1
			val ids = new HashSet[Int]()
			for (y <- 0 until number_contracts) {
				val dfId = nextRandom(ids)
				val df2 = xd.range(lim_inf,lim_sup)
				val df2Ans = df2
				.withColumn("customer_id", lit(dfId))
				.withColumn("closing_date",lit("99991231"))
				.withColumn("entity_id", lit(dfId % 9999))
				.withColumn("country_id", getRandomValue(country_id))
				.withColumn("contract_id", lit((1000000000 * (y+1)) + dfId))
				.withColumn("dependent_id", lit(dfId % 99999999))
				.withColumn("entity_page_id",lit("0182"))
				.withColumn("branch_page_id", lit(dfId % 99))
				.withColumn("counterpart_id", lit(dfId % 9999))
				.withColumn("ht_page_id", lit(dfId % 9999))
				.withColumn("owner_branch_id", lit(dfId % 9999))
				.withColumn("sale_country_id", getRandomValue(sale_country_id))
				.withColumn("sale_entity_id", lit(dfId % 9999))
				.withColumn("sale_branch_id", lit(dfId % 99))
				.withColumn("acc_branch_id", lit(dfId % 9999))
				.withColumn("proposed_start_date",lit("20190101"))
				.withColumn("proposed_conv_date",lit("20190101"))
				.withColumn("proposed_print_date",lit("20190101"))
				.withColumn("contract_regist_date",lit("20190101"))
				.withColumn("current_expiry_date",lit("99991231"))
				.withColumn("contract_cancel_date",lit("99991231"))
				.withColumn("cont_effective_date",lit("20190101"))
				.withColumn("kgct_last_change_date",lit("20190101"))
				.withColumn("operational_status_id",getRandomValue(operational_status_id))
				.withColumn("distr_channel_id",getRandomValue(distr_channel_id))
				.withColumn("distribution_means_id",getRandomValue(distribution_means_id))
				.withColumn("destination_id", lit(dfId % 99))
				.withColumn("subdestination_id", getRandomValue(subdestination_id))
				.withColumn("commercial_situation_id",lit("Situación comercial. Se informará a futuro."))
				.withColumn("sale_unit_id",getRandomValue(sale_unit_id))
				.withColumn("kgct_prodserv_id",getRandomValue(kgct_prodserv_id))
				.withColumn("kgct_subproduct_id",getRandomValue(kgct_subproduct_id))
				.withColumn("kgct_offer_id", getRandomValue(kgct_offer_id))
				.withColumn("financial_product_id", getRandomValue(financial_product_id))
				.withColumn("currency_id", getRandomValue(currency_id))
				.withColumn("language_id", getRandomValue(language_id))
				.withColumn("conf_level_id", getRandomValue(conf_level_id))
				.withColumn("accounting_guarantee_id",lit("Garantía contable"))
				.withColumn("contract_manag_app_id", getRandomValue(contract_manag_app_id))
				.withColumn("collective_id", getRandomValue(collective_id))
				.withColumn("contract_class_type", getRandomValue(contract_class_type))
				.withColumn("contract_blocked_type",getRandomValue(contract_blocked_type))
				.withColumn("securitized_type", getRandomValue(securitized_type))
				.withColumn("multi_curr_type", getRandomValue(multi_curr_type))
				.withColumn("registry_inscription_type", getRandomValue(registry_inscription_type))
				.withColumn("epa_type", getRandomValue(epa_type))
				.withColumn("min_signing_number", lit(dfId % 9999))
				.withColumn("collector_user_id", lit(dfId % 999999))
				.withColumn("user_id", lit(dfId % 999999))
				.withColumn("audit_user_id", lit(dfId % 999999))
				.withColumn("audit_date",lit("20190101"))
				.withColumn("pg_managed_date",lit("20190101"))
				.withColumn("pg_managed_hms_date",lit("23:59:59"))
				.withColumn("pg_managed_type", getRandomValue(pg_managed_type))
				.withColumn("origin_type", getRandomValue(origin_type))
				df2Ans.write.mode("append").format(arguments.format()).save(file_path(contracts))
			}
	 }
 }


	def create_data_clients(rowsLoop: Int, loops: Int) {
		val ids = new HashSet[Int]()
		for (x <- 0 until loops) {
		  val lim_inf=1+(x*rowsLoop)
			val lim_sup=1+((x+1)*rowsLoop)
			val df = xd.range(lim_inf,lim_sup)
			val dfId = nextRandom(ids)
			val dfAns = df
			.withColumn("entity_id",lit("0182"))
			.withColumn("customer_id", lit(dfId))
			.withColumn("crm_country_id",getRandomValue(crm_country_id))
			.withColumn("personal_id",concat(lit(dfId + 10000000) + lit(getRandomValue(upperCaseLetters))))
			.withColumn("personal_doc_type", getRandomValue(personal_doc_type))
			.withColumn("pers_doc_subtype_type", getRandomValue(pers_doc_subtype_type))
			.withColumn("personal_type",concat(lit("personal_type"), lit(dfId)))
			.withColumn("personal_second_id",concat(lit("personal_second_id"), lit(dfId)))
			.withColumn("customer_name", getRandomValue(customer_name))
			.withColumn("first_sur_name",getRandomValue(first_sur_name))
			.withColumn("second_sur_name",getRandomValue(second_sur_name))
			.withColumn("initial_modif_date",lit("20190101"))
			.withColumn("entry_date",lit("20190101"))
			.withColumn("contract_blocked_type", getRandomValue(contract_blocked_type))
			.withColumn("vip_flag_customer_type",getRandomValue(vip_flag_customer_type))
			.withColumn("main_office_id",lit("0182"))
			.withColumn("occupation_id", getRandomValue(occupation_id))
			.withColumn("data_institution_id",concat(lit("data_institution_id"), lit(dfId)))
			.withColumn("ucr_institution_id", getRandomValue(ucr_institution_id))
			.withColumn("client_type_id", getRandomValue(client_type_id))
			.withColumn("scope_type", getRandomValue(scope_type))
			.withColumn("cirbe_stat_soc_form_id", getRandomValue(cirbe_stat_soc_form_id))
			.withColumn("age_id", getRandomValue(age_id))
			.withColumn("role_type",getRandomValue(role_type))
			.withColumn("gender_type",getRandomValue(gender_type))
			.withColumn("treatment_code_id",getRandomValue(treatment_code_id))
			.withColumn("marital_status_id",getRandomValue(marital_status_id))
			.withColumn("custom_marriage_type",getRandomValue(custom_marriage_type))
			.withColumn("birth_date",lit("20190101"))
			.withColumn("birth_place_name",getRandomValue(birth_place_name))
			.withColumn("birth_province_name", getRandomValue(birth_province_name))
			.withColumn("birth_country_long_id", getRandomValue(birth_country_long_id))
			.withColumn("national_country_id", getRandomValue(national_country_id))
			.withColumn("nat_countrysf_id", getRandomValue(nat_countrysf_id))
			.withColumn("residence_date",lit("20190101"))
			.withColumn("applied_education_id", lit(dfId % 10))
			.withColumn("int_languagesf_id",getRandomValue(int_languagesf_id))
			.withColumn("cust_telephone_number", lit(600000000 + dfId % 99999999))
			.withColumn("cust_mobile_number", lit(900000000 + dfId % 99999999))
			.withColumn("cust_email_name",concat(lit("email_cliente_"), lit(dfId), lit("@gmail.com")))
			.withColumn("nat_occupation_code_id",lit(dfId % 5))
			.withColumn("code_cnae_id",lit(1110 + dfId % 100))
			.withColumn("asnef_type",getRandomValue(asnef_type))
			.withColumn("activity_end_type",getRandomValue(activity_end_type))
			.withColumn("legal_proc_type", getRandomValue(legal_proc_type))
			.withColumn("special_situations_id", lit(dfId % 12))
			.withColumn("refund_type",getRandomValue(refund_type))
			.withColumn("consolidation_id", lit(dfId % 8))
			.withColumn("remoteness_cust_group_type", lit(dfId % 9))
			.withColumn("bbva_contigo_type",getRandomValue(bbva_contigo_type))
			.withColumn("pub_serv_pos_type",getRandomValue(pub_serv_pos_type))
			.withColumn("place_job_id",concat(lit("place_job_id_"), lit(dfId)))
			.withColumn("job_position_desc",concat(lit("job_position_desc_"), lit(dfId)))
			.withColumn("street_type_id",getRandomValue(street_type_id))
			.withColumn("street_name",getRandomValue(street_name))
			.withColumn("street_number", lit(dfId % 100))
			.withColumn("postal_code_id", lit(dfId % 99999))
			.withColumn("fiscal_province_id", lit(dfId % 100))
			.withColumn("fiscal_province_desc",concat(lit("Descripción provincia "), lit(dfId)))
			.withColumn("address_rest_desc",concat(lit("Descripción domicilio "), lit(dfId)))
			.withColumn("foreign_postal_code_id", lit(dfId % 99999))
			.withColumn("auth_addr_type_id", getRandomValue(auth_addr_type_id))
			.withColumn("mail_language_id",getRandomValue(mail_language_id))
			.withColumn("robinson_date",lit("20190101"))
			.withColumn("robinson_type",getRandomValue(robinson_type))
			.withColumn("cust_risk_date",lit("20190101"))
			.withColumn("risk_type",getRandomValue(risk_type))
			.withColumn("anonym_data_date",lit("20190101"))
			.withColumn("anonym_data_type",getRandomValue(anonym_data_type))
			.withColumn("web_mov_categ_date",lit("20190101"))
			.withColumn("web_mov_categ_type",getRandomValue(web_mov_categ_type))
			.withColumn("legit_interest_date",lit("20190101"))
			.withColumn("legit_interest_type", getRandomValue(legit_interest_type))
			.withColumn("against_fraud_stu_date",lit("20190101"))
			.withColumn("against_fraud_stu_type",getRandomValue(against_fraud_stu_type))
			.withColumn("restr_post_date",lit("20190101"))
			.withColumn("restr_post_type",getRandomValue(restr_post_type))
			.withColumn("restr_voice_date",lit("20190101"))
			.withColumn("restr_voice_type",getRandomValue(restr_voice_type))
			.withColumn("restr_mail_date",lit("20190101"))
			.withColumn("restr_mail_type",getRandomValue(restr_mail_type))
			.withColumn("restr_cess_date",lit("20190101"))
			.withColumn("restr_cesio_type", getRandomValue(restr_cesio_type))
			.withColumn("sign_lopd_date",lit("20190101"))
			.withColumn("lopd_signed_type",getRandomValue(lopd_signed_type))
			.withColumn("restr_sms_date",lit("20190101"))
			.withColumn("restr_sms_type",getRandomValue(restr_sms_type))
			.withColumn("origin_system_type", getRandomValue(origin_system_type))
			.withColumn("finanzia_type", lit(dfId % 3))
			.withColumn("current_customer_id", lit(dfId % 99999999))
			.withColumn("cirbe_id", lit(dfId % 99999999))
			.withColumn("accounting_sector_id", lit(dfId % 4))
			.withColumn("acc_subsector_data_id", lit(dfId % 10))
			.withColumn("short_employee_id",getRandomValue(short_employee_id))
			.withColumn("job_ant_year_number",lit("20190101"))
			.withColumn("job_antiquity_month_number",lit("20190101"))
			.withColumn("profession_code_id", lit(dfId % 10))
			.withColumn("turnover_billing_amount", lit(dfId % 99999999))
			.withColumn("employees_ent_number", lit(dfId % 9999))
			.withColumn("cancellation_date",lit("20190101"))
			dfAns.write.mode("append").format(arguments.format()).save(file_path(clients))
		}
	}


	/**Do Job
	 **/
	def doJob() {
		create_data_clients(arguments.rowLoops(), arguments.loops())
		create_data_contracts(arguments.rowLoops(), arguments.loops())

		/**xd.sql("DROP TABLE IF EXISTS t_eclc_cust_basic_data_d")
		xd.sql("DROP TABLE IF EXISTS t_eags_contract_master_dep_d")
		xd.sql("CREATE TABLE t_eclc_cust_basic_data_d USING parquet OPTIONS (path '" + file_path(clients) + "', header 'true', inferSchema 'true')")
		xd.sql("CREATE TABLE t_eags_contract_master_dep_d USING parquet OPTIONS (path '" + file_path(contracts) + "', header 'true', inferSchema 'true')")
		xd.sql("SHOW TABLES")

		xd.sql("describe extended t_eclc_cust_basic_data_d").show()
		xd.sql("describe extended t_eags_contract_master_dep_d").show()

		xd.sql("SELECT count(*) FROM t_eclc_cust_basic_data_d").show()
		xd.sql("SELECT count(*) FROM t_eags_contract_master_dep_d").show()

		xd.sql("SELECT * FROM t_eclc_cust_basic_data_d limit 2").show()
		xd.sql("SELECT * FROM t_eags_contract_master_dep_d limit 2").show()*/
		
	}

  doJob



}