package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Integer> listVittorieInCasa(String squadra) {
		String sql = 	"	SELECT YEAR(m.Date) as anno, COUNT(*) AS peso " + 
						"	FROM matches m " + 
						"	WHERE m.HomeTeam = ? " + 
						"	AND m.FTR = 'H' " + 
						"	GROUP BY YEAR(m.Date) order by anno ASC";
		
		Map<Integer, Integer> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getInt("anno"), res.getInt("peso")*3);
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Integer> listVittorieInTrasferta(String squadra) {
		String sql = 	"	SELECT YEAR(m.Date) as anno, COUNT(*) AS peso " + 
						"	FROM matches m " + 
						"	WHERE m.AwayTeam = ? " + 
						"	AND m.FTR = 'A' " + 
						"	GROUP BY YEAR(m.Date) order by anno ASC";
		
		Map<Integer, Integer> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getInt("anno"), res.getInt("peso")*3);
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Integer> listPareggiInTrasferta(String squadra) {
		String sql = 	"	SELECT YEAR(m.Date) as anno, COUNT(*) AS peso " + 
						"	FROM matches m " + 
						"	WHERE m.AwayTeam = ? " + 
						"	AND m.FTR = 'D' " + 
						"	GROUP BY YEAR(m.Date) order by anno ASC";
		
		Map<Integer, Integer> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getInt("anno"), res.getInt("peso"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Map<Integer, Integer> listPareggiInCasa(String squadra) {
		String sql = 	"	SELECT YEAR(m.Date) as anno, COUNT(*) AS peso " + 
						"	FROM matches m " + 
						"	WHERE m.HomeTeam = ? " + 
						"	AND m.FTR = 'D' " + 
						"	GROUP BY YEAR(m.Date) order by anno ASC";
		
		Map<Integer, Integer> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getInt("anno"), res.getInt("peso"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams order by team ASC";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
