import { View, Text, StyleSheet, Button, TouchableOpacity, Dimensions } from 'react-native';
import { CameraView, CameraType, useCameraPermissions } from 'expo-camera';
import React, { useState } from 'react';
import { LinearGradient } from 'expo-linear-gradient';
import Icon from 'react-native-vector-icons/FontAwesome';

export default function QRScannerScreen({ navigation }) {
  const [cameraViewing, setCameraViewing] = useState(false);

  return (
    <View style={styles.qrScanner}>
      <LinearGradient colors={['#002436', '#24B4FC']} style={styles.gradient}>
        {cameraViewing ? <CameraScanner navigation={navigation} /> :
          <>
            <TouchableOpacity style={styles.button} onPress={() => setCameraViewing(true)}>
              <Icon name='qrcode' size={100} />
            </TouchableOpacity>
            <Text style={styles.text}>Escaneie o QR Code</Text>
          </>
        }
        {/* <Button title='Resultado' onPress={() => { setCameraViewing(false); navigation.navigate('Resultado') }}></Button> */}
        <Text style={[styles.text, { position: 'absolute', bottom: 1 }]}>questionarium.com.br</Text>
      </LinearGradient>
    </View>
  )
}

function CameraScanner({navigation}) {
  const [permission, requestPermission] = useCameraPermissions();
  const [facing, setFacing] = useState('back');
  const [scanned, setScanned] = useState(false);

  if (!permission) {
    // Camera permissions are still loading.
    return <View />;
  }

  if (!permission.granted) {
    // Camera permissions are not granted yet.
    return (
      <View style={{ marginBottom: 20 }}>
        <Text style={styles.message}>We need your permission to show the camera</Text>
        <Button onPress={requestPermission} title="grant permission" />
      </View>
    );
  }

  function toggleCameraFacing() {
    setFacing(current => (current === 'back' ? 'front' : 'back'));
  }

  function handleBarCodeScanned(scanResult) {
    // https://stackoverflow.com/questions/64797111/react-native-expo-39-scan-barcodes-using-expo-camera-in-web
    console.log({ scanResult });
    const { type, data } = scanResult;
    setScanned(true);
    // alert(data);
    setTimeout(() => {
      setScanned(false);
    }, 1000);
    navigation.navigate('Resultado');
  }

  return (
    <CameraView facing={facing} style={{ width: '80%', height: '80%' }}
      barcodeScannerSettings={{ barcodeTypes: ["qr"] }} 
      onBarcodeScanned={scanned ? null : handleBarCodeScanned}
      >
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.button2} onPress={toggleCameraFacing}>
          <Text style={styles.text2}>Flip Camera</Text>
        </TouchableOpacity>
      </View>
    </CameraView>
  );
}

const styles = StyleSheet.create({
  qrScanner: {
    flex: 1,
    justifyContent: 'center'
  },
  gradient: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  message: {
    textAlign: 'center',
    paddingBottom: 10,
    color: '#FFF'
  },
  text: {
    color: '#FFF',
    fontSize: 18,
    marginBottom: 20,
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#C6D4FF',
    borderRadius: Math.round(Dimensions.get('window').width + Dimensions.get('window').height) / 2,
    width: Dimensions.get('window').width * 0.3,
    height: Dimensions.get('window').width * 0.3,
    // https://stackoverflow.com/questions/30404067/creating-css-circles-in-react-native
    borderWidth: 1
  },
  buttonContainer: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: 'transparent',
    margin: 64,
  },
  button2: {
    flex: 1,
    alignSelf: 'flex-end',
    alignItems: 'center',
  },
  text2: {
    fontSize: 24,
    fontWeight: 'bold',
    color: 'white',
  },
})


