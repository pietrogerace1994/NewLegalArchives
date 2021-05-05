package eng.la.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;

import eng.la.model.ParteCorrelata;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* This source file was created by ICTeam S.P.A.
*
* The software must not be used nor reproduced in any form without
* ICTeam and customer authorization
*
* Author : $Author$
* File : $HeadURL$
* Revision : $Revision$
* Last Checkin: $Date$
*
*/
public final class ParteCorrelataRowMapper implements RowMapper
{
  private static final ParteCorrelataRowMapper INSTANCE = new ParteCorrelataRowMapper();

  private ParteCorrelataRowMapper(){}

  public static ParteCorrelataRowMapper getInstance()
  {
    return INSTANCE;
  }

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    ParteCorrelata parteCorrelata = new ParteCorrelata();
    parteCorrelata.setDenominazione(rs.getString("DENOMINAZIONE"));
    parteCorrelata.setCodFiscale(rs.getString("COD_FISCALE"));
    parteCorrelata.setPartitaIva(rs.getString("PARTITA_IVA"));    
    parteCorrelata.setRapporto(rs.getString("RAPPORTO"));
    parteCorrelata.setFamiliare(rs.getString("FAMILIARE"));
    parteCorrelata.setConsiglieriSindaci(rs.getString("CONSIGLIERI_SINDACI"));
    parteCorrelata.setDataCancellazione(rs.getDate("DATA_FINE_VALIDITA"));
    return parteCorrelata;
  }
}
