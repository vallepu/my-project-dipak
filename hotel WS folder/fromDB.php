<?php
//error_reporting(E_ALL);
//ini_set("display_errors", 1); 

define('DBSERVER' , 'localhost');
define('DSN', 'ckis');
//define('DSN', 'CKIS_LIVE_DB');
define('DBNAME', 'CKIS_DB');
define('DBUSER', 'webuser');
define('DBPWD', 'webuser');
?>
<?php
  
 class DB
  {
        private static $_connection = null;
        	
  		public static function get()
		{
			static $db = null;
			if( $db == null )
				$db = new DB();
			return $db;
		}
		
		private function construct()
		{
			try{
                if(!self::$_connection = odbc_connect( DSN , DBUSER  , DBPWD))
                {
                	throw new Exception('Unable to connect to database;');
                }                            
			}
            catch( Exception $e ){
              throw new Exception( $e->getMessage());              
            }
		}
		
		public static function connection()
		{
			if( empty( self::$_connection )){
                	self::construct();
                }
			return self::$_connection;
		}
		
		public function fetchData($sql)
		{	
			try
			{		
				$res			= odbc_exec(self::connection(), $sql);	//execute sql query	using db connection
				/*var_dump($res);*/
				if(!$res)		
				{
					throw new Exception('odbc error code: '.odbc_error().'\n sql query: '.$sql);
				}
				$arrReturn		= array();								//define array to collect channel data
				//echo odbc_num_rows($res);
				
				while($data	= odbc_fetch_array($res)){ 					// check for all records returned
					$arrReturn[]	= $data;							//fetch data into return array from db resultset
				}
				self::closeConnection();								// close db connection	
				return $arrReturn;										//return array back
			}
			catch (Exception $e)
			{
				throw new Exception( $e->getMessage());
			}
		}
		
		public function executeQuery($sql)
		{	
			//echo $sql;		
			$res			= odbc_exec(self::connection(), $sql);	//execute sql query	using db connection	
			if(!$res)		
			{
				throw new Exception(odbc_error());
			}
			self::closeConnection();								// close db connection	
			return $res;											//return result resource
		}
		
		public function closeConnection()
		{
			if( !empty( self::$_connection )){
				odbc_close(self::$_connection);
			}
		}

  }
  
?>




