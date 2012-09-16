package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.Attribute;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class Attribute extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const NAME:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.Attribute.name", "name", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var name:String;

		/**
		 *  @private
		 */
		public static const VALUE:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.Attribute.value", "value", (2 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		private var value$field:String;

		public function clearValue():void {
			value$field = null;
		}

		public function get hasValue():Boolean {
			return value$field != null;
		}

		public function set value(value:String):void {
			value$field = value;
		}

		public function get value():String {
			return value$field;
		}

		/**
		 *  @private
		 */
		public static const ATTRIBUTES:RepeatedFieldDescriptor$TYPE_MESSAGE = new RepeatedFieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.Attribute.attributes", "attributes", (3 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, Attribute);

		[ArrayElementType("Attribute")]
		public var attributes:Array = [];

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.name);
			if (hasValue) {
				com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 2);
				com.google.protobuf.WriteUtils.write$TYPE_STRING(output, value$field);
			}
			for (var attributes$index:uint = 0; attributes$index < this.attributes.length; ++attributes$index) {
				com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 3);
				com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.attributes[attributes$index]);
			}
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var name$count:uint = 0;
			var value$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (name$count != 0) {
						throw new flash.errors.IOError('Bad data format: Attribute.name cannot be set twice.');
					}
					++name$count;
					this.name = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 2:
					if (value$count != 0) {
						throw new flash.errors.IOError('Bad data format: Attribute.value cannot be set twice.');
					}
					++value$count;
					this.value = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 3:
					this.attributes.push(com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, new Attribute()));
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}
