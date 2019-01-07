PASSWORD="123456"
DN_SERVER="CN=server"
DN_CLIENT="CN=client"
DN_UNTRUSTED="CN=untrusted"
VALIDITY=7300
HOST=$1
DIR=$2

# clean up
cd ${DIR}
rm -f client.* server.* client2.* server2.* untrusted.*

function create_keystore
{
  KEY_FILE=$1
  DN=$2
  PASS=$3
  HOST=$4

  if [ -z "$HOST" ]
  then
    HOST='127.0.0.1'
  fi

  keytool -genkey \
        -keyalg RSA \
        -alias $DN \
        -keystore $KEY_FILE\
        -storepass $PASS \
        -validity $VALIDITY \
        -keysize 2048 \
        -keypass $PASS \
        -dname 'CN=Tomie, OU=Tomie, O=Tomie, L=Sliac, ST=Slovakia, C=SK' \
        -ext "SAN=dns:localhost,ip:$HOST"
  # export as PKCS12
  keytool -importkeystore -srckeystore $KEY_FILE \
     -destkeystore ${KEY_FILE%.*}.p12 -deststoretype PKCS12 \
     -srcstorepass $PASS -deststorepass $PASS
}

function export_cert
{
  KEY_FILE=$1
  ALIAS=$2
  EXPORT_FILE=$3
  PASS=$4
  keytool -export -alias $ALIAS -keystore $KEY_FILE \
     -storepass $PASS -file $EXPORT_FILE
}

function import_cert
{
  KEY_FILE=$1
  ALIAS=$2
  IMPORT_FILE=$3
  PASS=$4
  keytool -import -noprompt -alias $ALIAS -keystore $KEY_FILE \
     -storepass $PASS -file $IMPORT_FILE
}

create_keystore "server.keystore" "$DN_SERVER" "$PASSWORD" "$HOST"
create_keystore "server2.keystore" "$DN_SERVER" "$PASSWORD" "199.111.68.7"
create_keystore "client.keystore" "$DN_CLIENT" "$PASSWORD" "$HOST"
create_keystore "untrusted.keystore" "$DN_UNTRUSTED" "$PASSWORD"

export_cert "server.keystore" "$DN_SERVER" "server.crt" "$PASSWORD"
export_cert "server2.keystore" "$DN_SERVER" "server2.crt" "$PASSWORD"
export_cert "client.keystore" "$DN_CLIENT" "client.crt" "$PASSWORD"
export_cert "untrusted.keystore" "$DN_UNTRUSTED" \
            "untrusted.crt" "$PASSWORD"

import_cert "server.truststore" "$DN_CLIENT" "client.crt" "$PASSWORD"
import_cert "client.truststore" "$DN_SERVER" "server.crt" "$PASSWORD"
import_cert "client2.truststore" "$DN_SERVER" "server2.crt" "$PASSWORD"