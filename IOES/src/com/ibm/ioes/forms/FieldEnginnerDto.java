package com.ibm.ioes.forms;

public class FieldEnginnerDto{
		
		private Long se_Id;
		private String fieldEngineer;
		private String fieldEngineerHidden;
		private int field_Engineer_Is_Active;
		private long fieldEngineerId;

		public long getFieldEngineerId() {
			return fieldEngineerId;
		}
		public void setFieldEngineerId(long fieldEngineerId) {
			this.fieldEngineerId = fieldEngineerId;
		}
		public Long getSe_Id() {
			return se_Id;
		}
		public void setSe_Id(Long se_Id) {
			this.se_Id = se_Id;
		}
		public String getFieldEngineer() {
			return fieldEngineer;
		}
		public void setFieldEngineer(String fieldEngineer) {
			this.fieldEngineer = fieldEngineer;
		}
		public int getField_Engineer_Is_Active() {
			return field_Engineer_Is_Active;
		}
		public void setField_Engineer_Is_Active(int field_Engineer_Is_Active) {
			this.field_Engineer_Is_Active = field_Engineer_Is_Active;
		}
		public String getFieldEngineerHidden() {
			return fieldEngineerHidden;
		}
		public void setFieldEngineerHidden(String fieldEngineerHidden) {
			this.fieldEngineerHidden = fieldEngineerHidden;
		}
	}
