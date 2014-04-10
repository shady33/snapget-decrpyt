#!/usr/bin/env ruby
 
require 'openssl'
 
data = File.open('blob', 'r:ASCII-8BIT').read #here blob is the name of the input file
c = OpenSSL::Cipher.new('AES-128-ECB')
c.decrypt
c.key = 'M02cnQ51Ji97vwT4'
o = ''.force_encoding('ASCII-8BIT')
data.bytes.each_slice(16) { |s| o += c.update(s.map(&:chr).join) }
o += c.final
File.open('blob.jpg', 'w') { |f| f.write(o) } #here blob.jpg is the name of the output file