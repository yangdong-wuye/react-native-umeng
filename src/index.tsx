import { NativeModules } from 'react-native';

type UmengType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Umeng } = NativeModules;

export default Umeng as UmengType;
