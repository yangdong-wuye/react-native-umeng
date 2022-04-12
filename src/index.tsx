import { NativeModules } from 'react-native';

export interface InitOptions {
  appKey: string;
  channel: string;
  pushSecret: string;
}

type UmengType = {
  init(options: InitOptions): void;
  onPageStart(pageName: string): void;
  onPageEnd(pageName: string): void;
  onEvent(eventId: string): void;
  onEventWithLabel(eventId: string, eventLabel: string): void;
  onEventWithMap(eventId: string, map: Object): void;
  onEventWithMapAndCount(eventId: string, map: Object, value: number): void;
  onEventWithObject(eventId: string, map: Object): void;
};

const { Umeng } = NativeModules;

export default Umeng as UmengType;
